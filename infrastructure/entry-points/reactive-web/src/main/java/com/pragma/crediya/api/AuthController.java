package com.pragma.crediya.api;

import org.springframework.web.bind.annotation.RestController;

import com.pragma.crediya.api.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final JwtService jwtService;

    @GetMapping("/.well-known/jwks.json")
    public String getMethodName() {
        return jwtService.getJwkSet().toJSONObject().toString();
    }
}
