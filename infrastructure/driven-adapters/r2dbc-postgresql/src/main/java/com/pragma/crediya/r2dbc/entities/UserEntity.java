package com.pragma.crediya.r2dbc.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("usuario")
public record UserEntity(
    @Id Long id_usuario,
    String nombre,
    String apellido,
    String email,
    String documento_identidad,
    Date fecha_nacimiento,
    String direccion,
    String telefono,
    Double salario_base,
    String id_rol
    ) {
}
