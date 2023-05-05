package com.example.userservice.security.oauth.handler;

import com.example.security.common.JwtTokenCommon;
import com.example.security.model.UserAccountInfo;
import com.example.security.model.UserPrincipal;
import com.example.security.payload.UserToken;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userdatamodel.entity.enumtype.AuthProviderEnum;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.security.oauth.model.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserAccountRepository userAccountRepo;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtTokenCommon jwtTokenCommon;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (Objects.nonNull(authentication)) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

            String username = oauthUser.getEmail();
            Optional<UserAccount> userAccount = userAccountRepo.findByUsername(username);
            String clientName = oauthUser.getClientName();
            UserAccount account;
            if (!userAccount.isPresent()) {
                //register a new user

                String randomPassword = generateCommonLangPassword();
                account = userAccountRepo.save(UserAccount.builder()
                        .authProvider(AuthProviderEnum.findByCode(clientName))
                        .username(username)
                        .password(passwordEncoder.encode(randomPassword))
                        .fName(oauthUser.getName())
                        .role(AccountRoleEnum.ROLE_USER)
                        .build());
            } else {
                //update existing user

                account = userAccountRepo.save(UserAccount.builder()
                        .id(userAccount.get().getId())
                        .authProvider(AuthProviderEnum.findByCode(clientName))
                        .username(username)
                        .password(userAccount.get().getPassword())
                        .fName(oauthUser.getName())
                        .role(AccountRoleEnum.ROLE_USER)
                        .build());

            }

            UserPrincipal userDetails = UserPrincipal.builder()
                    .user(UserAccountInfo.builder()
                            .userId(account.getId())
                            .username(account.getUsername())
                            .build())
                    .authorities(oauthUser.getAuthorities())
                    .build();

            String accessToken = jwtTokenCommon.generateJwtToken(userDetails);
            String refreshToken = jwtTokenCommon.generateJwtRefreshToken(userDetails);

            Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            UserToken token = UserToken.builder()
                    .accountId(userDetails.getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .listRole(roles)
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserToken> entity = new HttpEntity<>(token, headers);
            String url = "http://localhost:8009/user/sign-in/oauth";
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

            response.sendRedirect("/api/user/oauth2/success/");
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private String generateCommonLangPassword() {

        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);

        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
