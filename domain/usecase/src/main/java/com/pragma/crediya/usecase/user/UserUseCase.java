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
        return userRepository.save(user);
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // public Mono<User> update(User user) {
    //     return userRepository.update(user);
    // }

    public Mono<Void> delete(String id) {
        return userRepository.delete(id);
    }

}
