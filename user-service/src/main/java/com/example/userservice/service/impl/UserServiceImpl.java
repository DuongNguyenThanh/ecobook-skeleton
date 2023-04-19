package com.example.userservice.service.impl;

import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserAccountRepository userAccountRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount userAccount = userAccountRepo.findByUsername(username);
        if(Objects.nonNull(userAccount)) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userAccount.getRole().name()));
            return new User(userAccount.getUsername(), userAccount.getPassword(), authorities);
        }else {
            log.error("User account not found");
            throw new UsernameNotFoundException("User account not found");
        }
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
                        if (u.getUsername().equals(request.getUsername())) {
                            response.setExistUsername(true);
                        } else if (u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistPhoneNumber(true);
                        } else if (u.getUsername().equals(request.getUsername()) && u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistUsername(true);
                            response.setExistPhoneNumber(true);
                        }
                    }
            );
        }

        return response;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        UserAccount userAccount = userAccountRepo.findByPhoneNum(request.getPhoneNum());

        if(Objects.nonNull(userAccount)) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            userAccountRepo.save(userAccount);
        } else {
            log.info("Phone number exists");
        }
    }
}