package com.pragma.crediya.model.user.gateways;

import java.util.List;
import java.util.Optional;

import com.pragma.crediya.model.user.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    // Define your repository methods here, for reactive repo
    Mono<User> save(User user);

    Mono<User> findById(Long id);

    Flux<User> findAll();

    Mono<Void> update(User user);

    Mono<Void> delete(Long id);
}
