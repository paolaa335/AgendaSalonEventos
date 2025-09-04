CREATE TABLE IF NOT EXISTS salon (
  salon_id       BIGINT NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(250) NOT NULL,
  direccion      VARCHAR(250) NOT NULL,
  capacidad      INT NOT NULL,
  precio_base    DECIMAL(12,2) NOT NULL,
  activo         TINYINT(1) NOT NULL DEFAULT 1,
  creado_en      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (salon_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_salon_nombre_direccion
  ON salon (nombre, direccion);
