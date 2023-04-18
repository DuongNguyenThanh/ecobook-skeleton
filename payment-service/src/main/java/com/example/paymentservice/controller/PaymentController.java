package com.example.paymentservice.controller;


import com.example.paymentservice.paypalconfig.config.PaypalPaymentIntent;
import com.example.paymentservice.paypalconfig.config.PaypalPaymentMethod;
import com.example.paymentservice.data.PaymentRepository;
import com.example.paymentservice.model.PaymentInfo;
import com.example.paymentservice.paypalconfig.service.PaypalService;
import com.example.paymentservice.paypalconfig.utils.Utils;
import com.example.paymentservice.request.PaymentRequest;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/payment", produces = "application/json")
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "/success";
    public static final String URL_PAYPAL_CANCEL = "/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private EntityLinks entityLinks;
    private PaymentRepository paymentRepository;
    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/")
    public Iterable<PaymentInfo> showAll(){
        return paymentRepository.findAll();
    }
    @GetMapping("/{paymentId}")
    public PaymentInfo showDetail(@PathVariable("paymentId") Integer paymentId){
        Optional<PaymentInfo> optionalPaymentInfo = paymentRepository.findById(paymentId);
        if(optionalPaymentInfo.isPresent()){
            return optionalPaymentInfo.get();
        }
        return null;
    }
    @GetMapping("/detail/{paypalId}")
    public String detailPayment(@PathVariable("paypalId") String paypalId) throws PayPalRESTException {
        return JSONFormatter.toJSON(Payment.get(paypalService.getApiContext(), String.valueOf(paypalId)));
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String pay(HttpServletRequest request, @RequestBody PaymentRequest pr ){
        String cancelUrl = Utils.getBaseURL(request) + "/api/v1/payment" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/api/v1/payment" + URL_PAYPAL_SUCCESS+"?userId="+
                pr.getUserId()+"&orderId="+pr.getOrderId();
        try {
            Payment payment = paypalService.createPayment(
                    pr.getSubTotal(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "cancel";
    }
    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                             @RequestParam("userId") Integer userId,@RequestParam("orderId") Integer orderId){
        try {
            //if user order token jj do
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setOrderId(orderId);
                paymentInfo.setUserId(userId);
                paymentInfo.setPaymentDate(new Date());
                paymentInfo.setAmount(Float.valueOf(payment.getTransactions().get(0).getAmount().getTotal()));
                paymentInfo.setPaymentType("paypal");
                paymentInfo.setStatus("da xu ly");
                paymentInfo.setPayerId(payerId);
                paymentInfo.setPaypalId(paymentId);
                PaymentInfo p = paymentRepository.save(paymentInfo);
                return JSONFormatter.toJSON(p);
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "null";
    }
}
