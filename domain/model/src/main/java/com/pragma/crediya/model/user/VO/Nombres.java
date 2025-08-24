package main.java.com.pragma.crediya.model.user.VO;

public record Nombres (String value) {
    public Nombres {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }
}
