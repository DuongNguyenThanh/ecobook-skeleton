package com.example.userservice.service;

import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<?> hello(String username);

    ResponseEntity<?> login(LoginRequest request);

    RegisterResponse addUser(RegisterRequest request);

    void resetPassword(ResetPasswordRequest request);

    ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
