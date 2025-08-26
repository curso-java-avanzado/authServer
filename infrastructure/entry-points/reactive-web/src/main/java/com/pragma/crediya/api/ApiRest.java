package com.pragma.crediya.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.crediya.api.DTOs.AuthRequestDTO;
import com.pragma.crediya.api.DTOs.AuthResponseDTO;
import com.pragma.crediya.api.DTOs.UserDTO;
import com.pragma.crediya.api.services.AuthService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
@Slf4j
public class ApiRest {

    private final AuthService authService;

    @PostMapping("/usuarios")
    @Transactional
    public Mono<ResponseEntity<AuthResponseDTO>> postMethodName(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating user: {}", userDTO.email());

        return authService.register(userDTO)
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
