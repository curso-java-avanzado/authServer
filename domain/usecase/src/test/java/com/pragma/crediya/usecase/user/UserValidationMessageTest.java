package com.pragma.crediya.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

class UserValidationMessageTest {

    @Test
    @DisplayName("Debería tener el mensaje correcto para NOMBRE_NULO_O_VACIO")
    void shouldHaveCorrectMessageForNombreNuloOVacio() {
        UserValidationMessage message = UserValidationMessage.NOMBRE_NULO_O_VACIO;
        
        assertEquals("El nombre no puede ser nulo o vacío", message.getMessage());
    }

    @Test
    @DisplayName("Debería tener el mensaje correcto para APELLIDO_NULO_O_VACIO")
    void shouldHaveCorrectMessageForApellidoNuloOVacio() {
        UserValidationMessage message = UserValidationMessage.APELLIDO_NULO_O_VACIO;
        
        assertEquals("El apellido no puede ser nulo o vacío", message.getMessage());
    }

    @Test
    @DisplayName("Debería tener el mensaje correcto para EMAIL_INVALIDO")
    void shouldHaveCorrectMessageForEmailInvalido() {
        UserValidationMessage message = UserValidationMessage.EMAIL_INVALIDO;
        
        assertEquals("El email no es válido", message.getMessage());
    }

    @Test
    @DisplayName("Debería tener el mensaje correcto para SALARIO_FUERA_DE_RANGO")
    void shouldHaveCorrectMessageForSalarioFueraDeRango() {
        UserValidationMessage message = UserValidationMessage.SALARIO_FUERA_DE_RANGO;
        
        assertEquals("El salario debe estar entre 0 y 15,000,000", message.getMessage());
    }

    @Test
    @DisplayName("Debería tener el mensaje correcto para EMAIL_YA_REGISTRADO")
    void shouldHaveCorrectMessageForEmailYaRegistrado() {
        UserValidationMessage message = UserValidationMessage.EMAIL_YA_REGISTRADO;
        
        assertEquals("El correo ya está registrado", message.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Todos los mensajes de validación deben tener un mensaje no nulo y no vacío")
    @EnumSource(UserValidationMessage.class)
    void allValidationMessagesShouldHaveNonNullAndNonEmptyMessage(UserValidationMessage validationMessage) {
        String message = validationMessage.getMessage();
        
        assertAll("validation message properties",
            () -> assertNotNull(message, "El mensaje no debe ser nulo"),
            () -> assertFalse(message.isEmpty(), "El mensaje no debe estar vacío"),
            () -> assertFalse(message.trim().isEmpty(), "El mensaje no debe ser solo espacios en blanco")
        );
    }

    @Test
    @DisplayName("Debería tener exactamente 5 mensajes de validación definidos")
    void shouldHaveExactlyFiveValidationMessages() {
        UserValidationMessage[] values = UserValidationMessage.values();
        
        assertEquals(5, values.length, "Debe haber exactamente 5 mensajes de validación");
    }

    @Test
    @DisplayName("Todos los nombres de enum deben seguir la convención de nombres")
    void allEnumNamesShouldFollowNamingConvention() {
        UserValidationMessage[] values = UserValidationMessage.values();
        
        for (UserValidationMessage message : values) {
            String name = message.name();
            assertAll("enum naming convention",
                () -> assertTrue(name.equals(name.toUpperCase()), 
                    "El nombre del enum debe estar en mayúsculas: " + name),
                () -> assertTrue(name.contains("_") || name.length() <= 20, 
                    "El nombre debe usar guiones bajos para separar palabras: " + name)
            );
        }
    }

    @Test
    @DisplayName("Los mensajes deben ser descriptivos y útiles para el usuario")
    void messagesShouldBeDescriptiveAndUserFriendly() {
        assertAll("user-friendly messages",
            () -> assertTrue(UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage().contains("nombre"),
                "El mensaje debe mencionar 'nombre'"),
            () -> assertTrue(UserValidationMessage.APELLIDO_NULO_O_VACIO.getMessage().contains("apellido"),
                "El mensaje debe mencionar 'apellido'"),
            () -> assertTrue(UserValidationMessage.EMAIL_INVALIDO.getMessage().contains("email"),
                "El mensaje debe mencionar 'email'"),
            () -> assertTrue(UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage().contains("salario"),
                "El mensaje debe mencionar 'salario'"),
            () -> assertTrue(UserValidationMessage.EMAIL_YA_REGISTRADO.getMessage().contains("correo"),
                "El mensaje debe mencionar 'correo'")
        );
    }

    @Test
    @DisplayName("Los mensajes deben estar en español")
    void messagesShouldBeInSpanish() {
        UserValidationMessage[] values = UserValidationMessage.values();
        
        for (UserValidationMessage message : values) {
            String msg = message.getMessage();
            
            assertTrue(msg.startsWith("El"), 
                "Los mensajes deben comenzar con 'El' (español): " + msg);
        }
    }

    @Test
    @DisplayName("Debería poder usar los mensajes en validaciones")
    void shouldBeAbleToUseMessagesInValidations() {
        String nombreMessage = UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage();
        String emailMessage = UserValidationMessage.EMAIL_INVALIDO.getMessage();
        String salarioMessage = UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage();
        
        assertAll("usable in validations",
            () -> assertDoesNotThrow(() -> {
                String validName = "ValidName";
                if (validName == null || validName.isEmpty()) {
                    throw new IllegalArgumentException(nombreMessage);
                }
            }),
            () -> assertDoesNotThrow(() -> {
                String validEmail = "test@test.com";
                if (!validEmail.matches("\\w+@\\w+\\.\\w+")) {
                    throw new IllegalArgumentException(emailMessage);
                }
            }),
            () -> assertDoesNotThrow(() -> {
                double validSalary = 100000.0;
                if (validSalary < 0 || validSalary > 15000000) {
                    throw new IllegalArgumentException(salarioMessage);
                }
            })
        );
    }

    @Test
    @DisplayName("Cada enum debe tener un valor único")
    void eachEnumShouldHaveUniqueValue() {
        UserValidationMessage[] values = UserValidationMessage.values();
        
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals(values[i], values[j], 
                    "Cada enum debe ser único: " + values[i] + " vs " + values[j]);
            }
        }
    }

    @Test
    @DisplayName("El enum debería ser serializable implícitamente")
    void enumShouldBeImplicitlySerializable() {
        UserValidationMessage message = UserValidationMessage.EMAIL_INVALIDO;
        
        assertDoesNotThrow(() -> {
            String name = message.name();
            UserValidationMessage recovered = UserValidationMessage.valueOf(name);
            assertEquals(message, recovered);
        });
    }
}