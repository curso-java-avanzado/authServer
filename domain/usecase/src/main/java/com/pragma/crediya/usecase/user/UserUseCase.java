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
        return Mono.just(user)
                .flatMap(u -> u.getNombre() == null || u.getNombre().isBlank() 
                    ? Mono.error(new IllegalArgumentException(UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage()))
                    : Mono.just(u))
                .flatMap(u -> u.getApellido() == null || u.getApellido().isBlank()
                    ? Mono.error(new IllegalArgumentException(UserValidationMessage.APELLIDO_NULO_O_VACIO.getMessage()))
                    : Mono.just(u))
                .flatMap(u -> u.getEmail() == null || !u.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
                    ? Mono.error(new IllegalArgumentException(UserValidationMessage.EMAIL_INVALIDO.getMessage()))
                    : Mono.just(u))
                .flatMap(u -> u.getSalario_base() == null || u.getSalario_base() < 0 || u.getSalario_base() > 15_000_000
                    ? Mono.error(new IllegalArgumentException(UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage()))
                    : Mono.just(u))
                .flatMap(validatedUser -> 
                    userRepository.findByEmail(validatedUser.getEmail())
                            .hasElement()
                            .flatMap(exists -> exists 
                                ? Mono.error(new IllegalArgumentException(UserValidationMessage.EMAIL_YA_REGISTRADO.getMessage()))
                                : Mono.just(validatedUser))
                );
    }
}
