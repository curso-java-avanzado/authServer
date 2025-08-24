package com.pragma.crediya.model.user;
import lombok.Builder;
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
    private Apellidos apellidos;
    private Date fecha_nacimiento;
    private String direccion;
    private String telefono;
    private CorreoElectronico correoElectronico;
    private SalarioBase salarioBase;
}
