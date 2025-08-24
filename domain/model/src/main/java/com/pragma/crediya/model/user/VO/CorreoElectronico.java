package main.java.com.pragma.crediya.model.user.VO;

public record CorreoElectronico(String value) {
    public CorreoElectronico {
        if (!value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El correo electrónico no es válido");
        }
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

    }
}
