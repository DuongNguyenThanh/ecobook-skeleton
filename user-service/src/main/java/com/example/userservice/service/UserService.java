package com.example.userservice.service;

import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> login(LoginRequest request);

    RegisterResponse addUser(RegisterRequest request);

    void resetPassword(ResetPasswordRequest request);
}
