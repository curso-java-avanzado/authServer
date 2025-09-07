package com.pragma.crediya.usecase.user;

import com.pragma.crediya.model.user.User;
import com.pragma.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userRepository);
    }

    private User createValidUser() {
        return User.builder()
                .id_usuario("USR001")
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@email.com")
                .password("password123")
                .documentoIdentidad("12345678")
                .fecha_nacimiento(LocalDate.of(1990, 5, 15))
                .direccion("Calle 123 #45-67")
                .telefono("3001234567")
                .salario_base(2500000.0)
                .id_rol("ROL001")
                .build();
    }

    @Test
    @DisplayName("Debería guardar usuario válido exitosamente")
    void shouldSaveValidUserSuccessfully() {
        User validUser = createValidUser();
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(validUser));

        StepVerifier.create(userUseCase.save(validUser))
                .expectNext(validUser)
                .verifyComplete();

        verify(userRepository).findByEmail(validUser.getEmail());
        verify(userRepository).save(validUser);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre es nulo")
    void shouldThrowExceptionWhenNameIsNull() {
        User userWithNullName = createValidUser();
        userWithNullName.setNombre(null);

        StepVerifier.create(userUseCase.save(userWithNullName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage()))
                .verify();

        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre está vacío")
    void shouldThrowExceptionWhenNameIsEmpty() {
        User userWithEmptyName = createValidUser();
        userWithEmptyName.setNombre("");

        StepVerifier.create(userUseCase.save(userWithEmptyName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage()))
                .verify();

        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre son solo espacios")
    void shouldThrowExceptionWhenNameIsOnlySpaces() {
        User userWithSpaceName = createValidUser();
        userWithSpaceName.setNombre("   ");

        StepVerifier.create(userUseCase.save(userWithSpaceName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.NOMBRE_NULO_O_VACIO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el apellido es nulo")
    void shouldThrowExceptionWhenLastNameIsNull() {
        User userWithNullLastName = createValidUser();
        userWithNullLastName.setApellido(null);

        StepVerifier.create(userUseCase.save(userWithNullLastName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.APELLIDO_NULO_O_VACIO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el apellido está vacío")
    void shouldThrowExceptionWhenLastNameIsEmpty() {
        User userWithEmptyLastName = createValidUser();
        userWithEmptyLastName.setApellido("");

        StepVerifier.create(userUseCase.save(userWithEmptyLastName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.APELLIDO_NULO_O_VACIO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el email es inválido")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        User userWithInvalidEmail = createValidUser();
        userWithInvalidEmail.setEmail("email-invalido");

        StepVerifier.create(userUseCase.save(userWithInvalidEmail))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.EMAIL_INVALIDO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el salario es nulo")
    void shouldThrowExceptionWhenSalaryIsNull() {
        User userWithNullSalary = createValidUser();
        userWithNullSalary.setSalario_base(null);

        StepVerifier.create(userUseCase.save(userWithNullSalary))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el salario es negativo")
    void shouldThrowExceptionWhenSalaryIsNegative() {
        User userWithNegativeSalary = createValidUser();
        userWithNegativeSalary.setSalario_base(-1000.0);

        StepVerifier.create(userUseCase.save(userWithNegativeSalary))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el salario excede el máximo")
    void shouldThrowExceptionWhenSalaryExceedsMaximum() {
        User userWithExcessiveSalary = createValidUser();
        userWithExcessiveSalary.setSalario_base(16000000.0);

        StepVerifier.create(userUseCase.save(userWithExcessiveSalary))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.SALARIO_FUERA_DE_RANGO.getMessage()))
                .verify();
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el email ya está registrado")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User existingUser = createValidUser();
        User newUser = createValidUser();
        newUser.setId_usuario("USR002");

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(existingUser));

        StepVerifier.create(userUseCase.save(newUser))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().equals(UserValidationMessage.EMAIL_YA_REGISTRADO.getMessage()))
                .verify();

        verify(userRepository).findByEmail(newUser.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Debería encontrar usuario por email exitosamente")
    void shouldFindUserByEmailSuccessfully() {
        User existingUser = createValidUser();
        String email = "juan.perez@email.com";

        when(userRepository.findByEmail(email)).thenReturn(Mono.just(existingUser));

        StepVerifier.create(userUseCase.findByEmail(email))
                .expectNext(existingUser)
                .verifyComplete();

        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Debería retornar vacío cuando el usuario no existe por email")
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        String email = "nonexistent@email.com";

        when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.findByEmail(email))
                .verifyComplete();

        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Debería permitir salario en el límite inferior (0)")
    void shouldAllowSalaryAtLowerBoundary() {
        User userWithMinSalary = createValidUser();
        userWithMinSalary.setSalario_base(0.0);

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(userWithMinSalary));

        StepVerifier.create(userUseCase.save(userWithMinSalary))
                .expectNext(userWithMinSalary)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debería permitir salario en el límite superior (15,000,000)")
    void shouldAllowSalaryAtUpperBoundary() {
        User userWithMaxSalary = createValidUser();
        userWithMaxSalary.setSalario_base(15000000.0);

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(userWithMaxSalary));

        StepVerifier.create(userUseCase.save(userWithMaxSalary))
                .expectNext(userWithMaxSalary)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debería validar emails válidos correctamente")
    void shouldValidateValidEmailsCorrectly() {
        User userWithValidEmail = createValidUser();
        userWithValidEmail.setEmail("test.user@domain.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(userWithValidEmail));

        StepVerifier.create(userUseCase.save(userWithValidEmail))
                .expectNext(userWithValidEmail)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debería manejar error del repositorio al buscar por email")
    void shouldHandleRepositoryErrorWhenFindingByEmail() {
        String email = "test@email.com";
        RuntimeException repositoryError = new RuntimeException("Error de base de datos");

        when(userRepository.findByEmail(email)).thenReturn(Mono.error(repositoryError));

        StepVerifier.create(userUseCase.findByEmail(email))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Debería manejar error del repositorio al guardar usuario")
    void shouldHandleRepositoryErrorWhenSavingUser() {
        User validUser = createValidUser();
        RuntimeException repositoryError = new RuntimeException("Error al guardar");

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.error(repositoryError));

        StepVerifier.create(userUseCase.save(validUser))
                .expectError(RuntimeException.class)
                .verify();
    }
}