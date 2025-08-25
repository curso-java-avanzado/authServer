package com.pragma.crediya.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

@Configuration
public class OpenApiConfig {

    @Bean
  OpenAPI openAPI() {
    var bearerScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearer-jwt", bearerScheme))
        // Quita esta línea si NO quieres exigir token globalmente
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
  }
}