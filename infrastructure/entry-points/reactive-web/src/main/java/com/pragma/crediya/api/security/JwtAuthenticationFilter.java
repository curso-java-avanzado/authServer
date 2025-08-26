package com.pragma.crediya.api.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

// TODO: Esto iría más bien en la clase que revisa las autorizaciones
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
    

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        
        try {
            String email = jwtService.extractEmail(token);
            String role = jwtService.extractRole(token);
            
            if (email != null && !jwtService.isTokenExpired(token)) {
                // Esto es muy importante porque aquí se settea un objeto Authentication que va dentro del securityContext y es lo que le indica
                // a la aplicación que el usuario está autenticado
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    email, null, authorities
                );
                
                return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
        } catch (Exception e) {
            log.error("Error processing JWT token: {}", e.getMessage());
        }

        return chain.filter(exchange);
    }
}