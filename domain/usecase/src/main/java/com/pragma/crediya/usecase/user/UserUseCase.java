package com.pragma.crediya.usecase.user;

import com.pragma.crediya.model.user.User;
import com.pragma.crediya.model.user.gateways.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> save(User user) {
        return validarUsuario(user)
                .flatMap(userRepository::save); // si pasa la validación, guarda
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Mono<User> validarUsuario(User user) {
        // Lógica de validación
        if (user.getNombre() == null || user.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (user.getApellido() == null || user.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
        }
        if (user.getEmail() != null && !user.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        if (user.getSalario_base() == null || user.getSalario_base() < 0 || user.getSalario_base() > 15_000_000) {
            throw new IllegalArgumentException("El salario debe estar entre 0 y 15,000,000");
        }

        // Validación reactiva: que el email no exista
        return userRepository.findByEmail(user.getEmail())
                .hasElement()
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        throw new IllegalArgumentException("El correo ya está registrado");
                    }
                    return Mono.just(user); // pasa la validación
                });

    }
}
