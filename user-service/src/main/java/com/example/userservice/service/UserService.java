package com.example.userservice.service;

import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;

public interface UserService {

    RegisterResponse addUser(RegisterRequest request);

    void resetPassword(ResetPasswordRequest request);
}
