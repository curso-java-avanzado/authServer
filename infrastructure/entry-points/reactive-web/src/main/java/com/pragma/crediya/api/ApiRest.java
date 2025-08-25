package com.pragma.crediya.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.crediya.api.DTOs.UserDTO;
import com.pragma.crediya.api.mapper.UserMapper;
import com.pragma.crediya.usecase.user.UserUseCase;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
@Slf4j
public class ApiRest { 
    
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    @GetMapping(path = "/usecase/path")
    public Mono<String> commandName() {
        // return useCase.doAction();
        log.info("testeando");
        return Mono.just("Hola mundo");
    }

    @PostMapping("/save")
    @Transactional
    public Mono<ResponseEntity<UserDTO>> postMethodName(@Valid @RequestBody UserDTO userDTO) {
        log.info("testeando 2.0");
        return userUseCase.save(userMapper.toModel(userDTO))
                .map(userMapper::toResponse) 
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get")
    public Mono<UserDTO> getUser() {
        return Mono.just(new UserDTO("John", "Doe", "john.doe@example.com", "123456789", LocalDate.now(), "123 Main St",
                "555-1234", 50000.0, "USER"));
    }

    @GetMapping("/getAll")
    public Flux<UserDTO> getAllUsers() {
        return userUseCase.findAll()
                .map(userMapper::toResponse);
    }

    @PostMapping("test")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> postMethodName() {
        //TODO: process POST request
        return Mono.just("post method");
    }
    
}
