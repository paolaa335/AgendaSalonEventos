-- V4: Tabla RESERVA (MySQL)
CREATE TABLE IF NOT EXISTS reserva (
                                       reserva_id BIGINT NOT NULL AUTO_INCREMENT,
                                       fecha TIMESTAMP NOT NULL,
                                       usuario_id BIGINT NOT NULL,
                                       salon_id BIGINT NOT NULL,
                                       creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       PRIMARY KEY (reserva_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    FOREIGN KEY (salon_id) REFERENCES salon(salon_id)
    );
