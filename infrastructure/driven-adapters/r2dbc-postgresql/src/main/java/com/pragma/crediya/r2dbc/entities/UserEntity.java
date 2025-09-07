package com.pragma.crediya.r2dbc.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    @Id @Column("id_usuario") String id_usuario;
    @Column("nombre") String nombre;
    @Column("apellido") String apellido;
    @Column("email") String email;
    @Column("password") String password;
    @Column("documento_identidad") String documentoIdentidad;
    @Column("fecha_nacimiento") LocalDate fecha_nacimiento;
    @Column("direccion") String direccion;
    @Column("telefono") String telefono;
    @Column("salario_base") Double salario_base;
    @Column("id_rol") String id_rol;
}
