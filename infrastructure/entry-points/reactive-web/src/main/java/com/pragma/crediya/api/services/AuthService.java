package com.pragma.crediya.api.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.nimbusds.jose.JOSEException;
import com.pragma.crediya.api.DTOs.AuthRequestDTO;
import com.pragma.crediya.api.DTOs.AuthResponseDTO;
import com.pragma.crediya.api.DTOs.UserDTO;
import com.pragma.crediya.api.DTOs.UserInfoDTO;
import com.pragma.crediya.api.mapper.UserMapper;
import com.pragma.crediya.api.security.JwtService;
import com.pragma.crediya.model.user.User;
import com.pragma.crediya.usecase.user.UserUseCase;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TransactionalOperator transactionalOperator;

    public Mono<AuthResponseDTO> register(UserDTO request) throws IllegalArgumentException{
        User user = User.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .documentoIdentidad(request.documentoIdentidad())
                .fecha_nacimiento(request.fecha_nacimiento())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .salario_base(request.salario_base())
                .id_rol(request.id_rol())
                .build();

        return userUseCase.save(user)
                .map(userMapper::toResponse)
                .map(userDto -> {
                    String token;
                    try {
                        token = jwtService.generateToken(userDto.email(), userDto.id_rol(), userDto.documentoIdentidad());
                        return AuthResponseDTO.of(token, userDto);
                    } catch (InvalidKeyException | JOSEException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .as(transactionalOperator::transactional);
    }

    public Mono<AuthResponseDTO> login(AuthRequestDTO request) {
        return userUseCase.findByEmail(request.email())
                .cast(User.class)
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(userMapper::toResponse)
                .map(userDto -> {
                    String token;
                    try {
                        token = jwtService.generateToken(userDto.email(), userDto.id_rol(), userDto.documentoIdentidad());
                        return AuthResponseDTO.of(token, userDto);
                    } catch (InvalidKeyException | JOSEException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .as(transactionalOperator::transactional)
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

    public Mono<UserInfoDTO> userInfo(String email) {
        return userUseCase.findByEmail(email)
        .map(userMapper::toUserInfoDTO)
                .as(transactionalOperator::transactional)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}