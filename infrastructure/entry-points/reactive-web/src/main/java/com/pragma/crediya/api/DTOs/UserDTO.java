package com.pragma.crediya.api.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    String nombre,

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede estar en blanco")
    String apellido,

    @Email(message = "El email no es válido")
    String email,

    String documento_identidad,
    LocalDate fecha_nacimiento,
    String direccion,
    String telefono,

    @NotNull(message = "El salario no puede ser nulo")
    @Min(value = 0, message = "El salario debe ser mayor o igual a 0")
    @Max(value = 15000000, message = "El salario debe ser menor o igual a 15000000")
    Double salario_base,

    String id_rol
    ) {
}


// Hazme un cuerpo postman con esto:
// @NotNull(message = "El nombre no puede ser nulo")
//     @NotBlank(message = "El nombre no puede estar en blanco")
//     String nombre,

//     @NotNull(message = "El apellido no puede ser nulo")
//     @NotBlank(message = "El apellido no puede estar en blanco")
//     String apellido,

//     @Email(message = "El email no es válido")
//     String email,

//     String documento_identidad,
//     Date fecha_nacimiento,
//     String direccion,
//     String telefono,

//     @NotNull(message = "El salario no puede ser nulo")
//     @Min(value = 0, message = "El salario debe ser mayor o igual a 0")
//     @Max(value = 15000000, message = "El salario debe ser menor o igual a 15000000")
//     Double salario_base,

