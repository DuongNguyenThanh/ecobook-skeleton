package com.example.userservice.controller;

import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAccountController {

    private final UserService userService;

    @GetMapping("/hello")
    ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
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
}
