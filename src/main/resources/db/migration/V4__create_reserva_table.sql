-- V4: Tabla RESERVA (MySQL) - Versión mejorada
CREATE TABLE IF NOT EXISTS reserva (
    reserva_id BIGINT NOT NULL AUTO_INCREMENT,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    usuario_id BIGINT NOT NULL,
    salon_id BIGINT NOT NULL,
    evento_id BIGINT NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (reserva_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    FOREIGN KEY (salon_id) REFERENCES salon(salon_id),
    FOREIGN KEY (evento_id) REFERENCES evento(evento_id),
    CONSTRAINT chk_fechas_validas CHECK (fecha_fin > fecha_inicio)
);

-- Índices para optimizar consultas
CREATE INDEX idx_reserva_fechas ON reserva(fecha_inicio, fecha_fin);
CREATE INDEX idx_reserva_estado ON reserva(estado);
CREATE INDEX idx_reserva_salon_fechas ON reserva(salon_id, fecha_inicio, fecha_fin);