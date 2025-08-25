package com.pragma.crediya.api.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    String nombre,

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede estar en blanco")
    String apellido,

    @Email(message = "El email no es válido")
    String email,

    @NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,

    String documento_identidad,
    LocalDate fecha_nacimiento,
    String direccion,
    String telefono,

    @NotNull(message = "El salario no puede ser nulo")
    @Min(value = 0, message = "El salario debe ser mayor o igual a 0")
    @Max(value = 15000000, message = "El salario debe ser menor o igual a 15000000")
    Double salario_base
) {
}