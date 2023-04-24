package com.example.userservice.controller;

import com.example.api.controller.BaseController;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAccountController extends BaseController {

    private final UserService userService;

    @GetMapping("/hello")
    ResponseEntity<?> helloWorld() {

        String username = getPrincipal();
        Long userId = getOriginalId();

        return ResponseEntity.ok(userService.hello(username, userId));
    }

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    ResponseEntity<RegisterResponse> addUser(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(userService.addUser(request));
    }

    @PostMapping("/reset-password")
    ResponseEntity<String> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Reset password completed");
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }
}
