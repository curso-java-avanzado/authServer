package com.pragma.crediya.r2dbc.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id Long id_usuario;
    String nombre;
    String apellido;
    String email;
    String documento_identidad;
    LocalDate fecha_nacimiento;
    String direccion;
    String telefono;
    Double salario_base;
    String id_rol;
}
