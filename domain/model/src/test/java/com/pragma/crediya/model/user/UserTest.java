package com.pragma.crediya.model.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class UserTest {

    @Test
    @DisplayName("Debería crear un usuario usando el builder")
    void shouldCreateUserUsingBuilder() {
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        
        User user = User.builder()
                .id_usuario("USR001")
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@email.com")
                .password("password123")
                .documentoIdentidad("12345678")
                .fecha_nacimiento(fechaNacimiento)
                .direccion("Calle 123 #45-67")
                .telefono("3001234567")
                .salario_base(2500000.0)
                .id_rol("ROL001")
                .build();

        assertAll("user properties",
            () -> assertEquals("USR001", user.getId_usuario()),
            () -> assertEquals("Juan", user.getNombre()),
            () -> assertEquals("Pérez", user.getApellido()),
            () -> assertEquals("juan.perez@email.com", user.getEmail()),
            () -> assertEquals("password123", user.getPassword()),
            () -> assertEquals("12345678", user.getDocumentoIdentidad()),
            () -> assertEquals(fechaNacimiento, user.getFecha_nacimiento()),
            () -> assertEquals("Calle 123 #45-67", user.getDireccion()),
            () -> assertEquals("3001234567", user.getTelefono()),
            () -> assertEquals(2500000.0, user.getSalario_base()),
            () -> assertEquals("ROL001", user.getId_rol())
        );
    }

    @Test
    @DisplayName("Debería crear un usuario usando constructor con argumentos")
    void shouldCreateUserUsingAllArgsConstructor() {
        LocalDate fechaNacimiento = LocalDate.of(1985, 10, 20);
        
        User user = new User(
                "USR002",
                "María",
                "González",
                "maria.gonzalez@email.com",
                "securePass456",
                "87654321",
                fechaNacimiento,
                "Carrera 45 #12-34",
                "3009876543",
                3000000.0,
                "ROL002"
        );

        assertEquals("USR002", user.getId_usuario());
        assertEquals("María", user.getNombre());
        assertEquals("González", user.getApellido());
        assertEquals("maria.gonzalez@email.com", user.getEmail());
    }

    @Test
    @DisplayName("Debería crear un usuario vacío usando constructor sin argumentos")
    void shouldCreateEmptyUserUsingNoArgsConstructor() {
        User user = new User();
        
        assertAll("empty user properties",
            () -> assertNull(user.getId_usuario()),
            () -> assertNull(user.getNombre()),
            () -> assertNull(user.getApellido()),
            () -> assertNull(user.getEmail()),
            () -> assertNull(user.getPassword()),
            () -> assertNull(user.getDocumentoIdentidad()),
            () -> assertNull(user.getFecha_nacimiento()),
            () -> assertNull(user.getDireccion()),
            () -> assertNull(user.getTelefono()),
            () -> assertNull(user.getSalario_base()),
            () -> assertNull(user.getId_rol())
        );
    }

    @Test
    @DisplayName("Debería permitir modificar propiedades usando setters")
    void shouldAllowModifyingPropertiesUsingSetters() {
        User user = new User();
        LocalDate fechaNacimiento = LocalDate.of(1992, 3, 8);
        
        user.setId_usuario("USR003");
        user.setNombre("Carlos");
        user.setApellido("Rodríguez");
        user.setEmail("carlos.rodriguez@email.com");
        user.setPassword("myPassword789");
        user.setDocumentoIdentidad("11223344");
        user.setFecha_nacimiento(fechaNacimiento);
        user.setDireccion("Avenida 80 #25-50");
        user.setTelefono("3151234567");
        user.setSalario_base(2800000.0);
        user.setId_rol("ROL003");

        assertAll("modified user properties",
            () -> assertEquals("USR003", user.getId_usuario()),
            () -> assertEquals("Carlos", user.getNombre()),
            () -> assertEquals("Rodríguez", user.getApellido()),
            () -> assertEquals("carlos.rodriguez@email.com", user.getEmail()),
            () -> assertEquals("myPassword789", user.getPassword()),
            () -> assertEquals("11223344", user.getDocumentoIdentidad()),
            () -> assertEquals(fechaNacimiento, user.getFecha_nacimiento()),
            () -> assertEquals("Avenida 80 #25-50", user.getDireccion()),
            () -> assertEquals("3151234567", user.getTelefono()),
            () -> assertEquals(2800000.0, user.getSalario_base()),
            () -> assertEquals("ROL003", user.getId_rol())
        );
    }

    @Test
    @DisplayName("Debería crear una copia modificada usando toBuilder")
    void shouldCreateModifiedCopyUsingToBuilder() {
        User originalUser = User.builder()
                .id_usuario("USR004")
                .nombre("Ana")
                .apellido("López")
                .email("ana.lopez@email.com")
                .salario_base(2200000.0)
                .build();

        User modifiedUser = originalUser.toBuilder()
                .salario_base(2700000.0)
                .telefono("3201234567")
                .build();

        assertAll("original user unchanged",
            () -> assertEquals("USR004", originalUser.getId_usuario()),
            () -> assertEquals("Ana", originalUser.getNombre()),
            () -> assertEquals("López", originalUser.getApellido()),
            () -> assertEquals(2200000.0, originalUser.getSalario_base()),
            () -> assertNull(originalUser.getTelefono())
        );

        assertAll("modified user has changes",
            () -> assertEquals("USR004", modifiedUser.getId_usuario()),
            () -> assertEquals("Ana", modifiedUser.getNombre()),
            () -> assertEquals("López", modifiedUser.getApellido()),
            () -> assertEquals(2700000.0, modifiedUser.getSalario_base()),
            () -> assertEquals("3201234567", modifiedUser.getTelefono())
        );
    }

    @Test
    @DisplayName("Debería manejar valores límite para salario base")
    void shouldHandleBoundarySalaryValues() {
        User userWithMinSalary = User.builder()
                .salario_base(0.0)
                .build();
        
        User userWithMaxSalary = User.builder()
                .salario_base(15000000.0)
                .build();

        assertEquals(0.0, userWithMinSalary.getSalario_base());
        assertEquals(15000000.0, userWithMaxSalary.getSalario_base());
    }

    @Test
    @DisplayName("Debería manejar fechas de nacimiento válidas")
    void shouldHandleValidBirthDates() {
        LocalDate fechaPasada = LocalDate.of(1980, 1, 1);
        LocalDate fechaReciente = LocalDate.of(2000, 12, 31);
        
        User user1 = User.builder().fecha_nacimiento(fechaPasada).build();
        User user2 = User.builder().fecha_nacimiento(fechaReciente).build();

        assertEquals(fechaPasada, user1.getFecha_nacimiento());
        assertEquals(fechaReciente, user2.getFecha_nacimiento());
    }
}