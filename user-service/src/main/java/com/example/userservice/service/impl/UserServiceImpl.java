package com.example.userservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.security.common.JwtTokenCommon;
import com.example.security.model.UserPrincipal;
import com.example.security.payload.UserToken;
import com.example.userdatamodel.entity.RefreshToken;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.service.RefreshTokenService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenCommon jwtTokenCommon;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        // Authenticate via authentication manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (Objects.nonNull(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

            String jwtToken = jwtTokenCommon.generateJwtToken(userDetails);

            Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            return ResponseEntity.ok(
                    UserToken.builder()
                            .accountId(userDetails.getId())
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken.getToken())
                            .listRole(roles)
                            .firstName(userDetails.getFirstName())
                            .lastName(userDetails.getLastName())
                            .expiresIn(refreshToken.getExpiryDate().getTime())
                            .build()
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.name());
    }

    @Override
    public RegisterResponse addUser(RegisterRequest request) {

        RegisterResponse response = new RegisterResponse();

        List<UserAccount> userAccounts = userAccountRepo.findAllByUsernameOrPhoneNum(
                Collections.singletonList(request.getUsername()), Collections.singletonList(request.getPhoneNum())
        );

        if(userAccounts.isEmpty()) {
            log.info("Saving new user {} to the database", request.getLName());
            userAccountRepo.save(UserAccount.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .fName(request.getFName())
                    .lName(request.getLName())
                    .address(request.getAddress())
                    .role(AccountRoleEnum.USER)
                    .phoneNum(request.getPhoneNum())
                    .build());
        }
        else {
            log.info("User Account already exist");
            userAccounts.forEach(
                    u -> {
                        if (u.getUsername().equals(request.getUsername()) && u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistUsername(1);
                            response.setExistPhoneNumber(1);
                        } else if (u.getUsername().equals(request.getUsername())) {
                            response.setExistUsername(1);
                        } else if (u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistPhoneNumber(1);
                        }
                    }
            );
        }

        return response;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        UserAccount userAccount = userAccountRepo.findByPhoneNum(request.getPhoneNum()).orElseThrow(
                () -> new NotFoundException(
                        String.format("resetPassword error: Not found User Account with phone_number: %s", request.getPhoneNum())
                )
        );

        if(Objects.nonNull(userAccount)) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            userAccountRepo.save(userAccount);
        } else {
            log.info("Phone number exists");
        }
    }
}