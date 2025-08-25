package com.pragma.crediya.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.crediya.api.DTOs.AuthRequestDTO;
import com.pragma.crediya.api.DTOs.AuthResponseDTO;
import com.pragma.crediya.api.DTOs.RegisterRequestDTO;
import com.pragma.crediya.api.security.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
        log.info("Registering user: {}", request.email());
        return authService.register(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(error -> {
                    log.error("Error registering user: {}", error.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request) {
        log.info("Login attempt for user: {}", request.email());
        return authService.login(request)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Login failed for user {}: {}", request.email(), error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }
}