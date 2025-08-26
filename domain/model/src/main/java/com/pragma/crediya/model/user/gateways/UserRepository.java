package com.pragma.crediya.model.user.gateways;

import com.pragma.crediya.model.user.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    // Define your repository methods here, for reactive repo
    Mono<User> save(User user);

    Mono<User> findByEmail(String email);
}
