package com.pragma.crediya.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pragma.crediya.r2dbc.entities.UserEntity;

import reactor.core.publisher.Mono;

public interface MyReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {

    Mono<UserEntity> findByEmail(String email);
}
