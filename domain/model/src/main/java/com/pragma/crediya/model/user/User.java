package com.pragma.crediya.model.user;
import lombok.Builder;

import java.sql.Date;

import com.pragma.crediya.model.user.VO.Apellidos;
import com.pragma.crediya.model.user.VO.Email;
import com.pragma.crediya.model.user.VO.Nombres;
import com.pragma.crediya.model.user.VO.SalarioBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Nombres nombre;
    private Apellidos apellido;
    private Email email;
    private String documento_identidad;
    private String telefono;
    private SalarioBase salario_base;
    private Date fecha_nacimiento;
    private String direccion;
}
