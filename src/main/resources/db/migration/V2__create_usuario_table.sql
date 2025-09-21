-- V2: Tabla USUARIO (MySQL)
CREATE TABLE IF NOT EXISTS usuario (
                                       usuario_id BIGINT NOT NULL AUTO_INCREMENT,
                                       nombre VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id),
    CONSTRAINT uq_usuario_email UNIQUE (email)
    );