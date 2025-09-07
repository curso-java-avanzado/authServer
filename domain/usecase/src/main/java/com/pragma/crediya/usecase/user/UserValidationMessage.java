package com.pragma.crediya.usecase.user;

public enum UserValidationMessage {
    NOMBRE_NULO_O_VACIO("El nombre no puede ser nulo o vacío"),
    APELLIDO_NULO_O_VACIO("El apellido no puede ser nulo o vacío"),
    EMAIL_INVALIDO("El email no es válido"),
    SALARIO_FUERA_DE_RANGO("El salario debe estar entre 0 y 15,000,000"),
    EMAIL_YA_REGISTRADO("El correo ya está registrado");

    private final String message;

    UserValidationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}