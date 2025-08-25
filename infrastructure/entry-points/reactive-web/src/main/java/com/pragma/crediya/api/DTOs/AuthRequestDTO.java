package com.pragma.crediya.api.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(
    @NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email no es válido")
    String email,
    
    @NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    String password
) {
}