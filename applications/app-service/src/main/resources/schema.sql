CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    documento_identidad VARCHAR(255),
    fecha_nacimiento DATE,
    direccion VARCHAR(255),
    telefono VARCHAR(255),
    salario_base DECIMAL(15,2),
    id_rol VARCHAR(255) DEFAULT 'USER'
);