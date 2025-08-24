package main.java.com.pragma.crediya.model.user.VO;

public record SalarioBase(Double value) {
    public SalarioBase {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("El salario base debe ser un número positivo");
        }
        if( value > 15000000) {
            throw new IllegalArgumentException("El salario base no puede ser mayor a 15,000,000");
        }
    }
    
}
