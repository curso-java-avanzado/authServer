package com.pragma.crediya.api.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pragma.crediya.api.DTOs.AuthRequestDTO;
import com.pragma.crediya.api.DTOs.AuthResponseDTO;
import com.pragma.crediya.api.DTOs.RegisterRequestDTO;
import com.pragma.crediya.api.mapper.UserMapper;
import com.pragma.crediya.model.user.User;
import com.pragma.crediya.usecase.user.UserUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Mono<AuthResponseDTO> register(RegisterRequestDTO request) {
        User user = User.builder()
                // NO asignar ID - dejar que la DB lo genere
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .documento_identidad(request.documento_identidad())
                .fecha_nacimiento(request.fecha_nacimiento())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .salario_base(request.salario_base())
                .id_rol("USER") // Rol por defecto
                .build();

        return userUseCase.save(user)
                .map(userMapper::toResponse)
                .map(userDto -> {
                    String token = jwtService.generateToken(userDto.email(), userDto.id_rol());
                    return AuthResponseDTO.of(token, userDto);
                });
    }

    public Mono<AuthResponseDTO> login(AuthRequestDTO request) {
        return userUseCase.findByEmail(request.email())
                .cast(User.class)
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(userMapper::toResponse)
                .map(userDto -> {
                    String token = jwtService.generateToken(userDto.email(), userDto.id_rol());
                    return AuthResponseDTO.of(token, userDto);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
}