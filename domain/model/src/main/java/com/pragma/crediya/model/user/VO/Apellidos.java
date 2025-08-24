package main.java.com.pragma.crediya.model.user.VO;

public record Apellidos(String value) {
    public Apellidos {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
    }
}
