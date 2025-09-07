package com.pragma.crediya.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
// Este servicio es para firmar mis JWT
public class JwtService {

    private RSAKey rsaKey;

    private long jwtExpiration = 86400000L; // 24h

    @PostConstruct
    public void initKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                    .privateKey((RSAPrivateKey) keyPair.getPrivate())
                    .keyID(UUID.randomUUID().toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error generando claves RSA", e);
        }
    }

    public String generateToken(String email, String role, String documentoIdentidad) throws InvalidKeyException, JOSEException {
        log.info(documentoIdentidad);
        return Jwts.builder()
                .subject(email)
                .claim("email", email)
                .claim("doc", documentoIdentidad)
                .claim("scp", Arrays.asList(role))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(rsaKey.toPrivateKey(), Jwts.SIG.RS256) // 🔑 clave privada
                .compact();
    }

    public String extractEmail(String token) throws JwtException, IllegalArgumentException, JOSEException {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) throws JwtException, IllegalArgumentException, JOSEException {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) throws JwtException, IllegalArgumentException, JOSEException {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) throws JwtException, IllegalArgumentException, JOSEException {
        return Jwts.parser()
                .verifyWith(rsaKey.toPublicKey()) // 🔑 clave pública
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Expone el JWKS (solo clave pública)
    public JWKSet getJwkSet() {
        return new JWKSet(rsaKey.toPublicJWK());
    }
}