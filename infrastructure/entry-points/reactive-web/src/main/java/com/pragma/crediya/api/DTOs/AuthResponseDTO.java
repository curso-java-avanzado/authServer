package com.pragma.crediya.api.DTOs;

public record AuthResponseDTO(
    String token,
    String type,
    String email,
    String nombre,
    String apellido
) {
    public static AuthResponseDTO of(String token, UserDTO user) {
        return new AuthResponseDTO(token, "Bearer", user.email(), user.nombre(), user.apellido());
    }
}