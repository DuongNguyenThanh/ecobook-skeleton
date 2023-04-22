package com.example.api.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class BaseController {

    protected UsernamePasswordAuthenticationToken getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            return (UsernamePasswordAuthenticationToken) auth;
        }
        return null;
    }

    protected String getPrincipal() {
        UsernamePasswordAuthenticationToken authentication = getAuthentication();
        if (Objects.nonNull(authentication)) {
            return authentication.getPrincipal().toString();
        }
        return null;
    }

}
