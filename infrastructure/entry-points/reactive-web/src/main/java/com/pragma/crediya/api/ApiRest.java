package com.pragma.crediya.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.crediya.api.DTOs.AuthRequestDTO;
import com.pragma.crediya.api.DTOs.AuthResponseDTO;
import com.pragma.crediya.api.DTOs.UserDTO;
import com.pragma.crediya.api.DTOs.UserInfoDTO;
import com.pragma.crediya.api.services.AuthService;
import com.pragma.crediya.model.user.User;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
@Slf4j
public class ApiRest {

    private final AuthService authService;

    @PostMapping("/usuarios")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_ASESOR')")
    public Mono<ResponseEntity<AuthResponseDTO>> postMethodName(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating user: {}", userDTO.email());

        return authService.register(userDTO)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request) {
        log.info("Login attempt for user: {}", request.email());
        return authService.login(request)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Login failed for user {}: {}", request.email(), error.getMessage());
                    throw new IllegalArgumentException("Credenciales inválidas");
                });
    }

    @GetMapping("/userInfo")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_ASESOR')")
    public Mono<UserInfoDTO> getMethodName(@RequestParam(value = "email") String email) {
        return authService.userInfo(email);
    }
}
