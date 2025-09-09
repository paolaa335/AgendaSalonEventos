-- V1: Tabla SALON (MySQL)
CREATE TABLE IF NOT EXISTS salon (
  salon_id       BIGINT NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(250) NOT NULL,
  direccion      VARCHAR(250) NOT NULL,
  capacidad      INT NOT NULL,
  precio_base    DECIMAL(12,2) NOT NULL,
  activo         TINYINT(1) NOT NULL DEFAULT 1,
  creado_en      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (salon_id),
  -- clave única IN-LINE
  UNIQUE KEY ux_salon_nombre_direccion (nombre, direccion),
  CHECK (capacidad > 0),
  CHECK (precio_base >= 0)

);
