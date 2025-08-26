package com.pragma.crediya.model.user;
import lombok.Builder;

import java.time.LocalDate;

// import com.pragma.crediya.model.user.VO.Apellidos;
// import com.pragma.crediya.model.user.VO.Email;
// import com.pragma.crediya.model.user.VO.Nombres;
// import com.pragma.crediya.model.user.VO.SalarioBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String documento_identidad;
    private LocalDate fecha_nacimiento;
    private String direccion;
    private String telefono;
    private Double salario_base;
    private String id_rol;

}