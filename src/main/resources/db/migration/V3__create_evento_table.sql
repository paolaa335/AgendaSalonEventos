-- V3: Tabla EVENTO (MySQL)
CREATE TABLE IF NOT EXISTS evento (
                                      evento_id BIGINT NOT NULL AUTO_INCREMENT,
                                      nombre VARCHAR(250) NOT NULL,
    descripcion VARCHAR(500),
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP NOT NULL,
    usuario_id BIGINT NOT NULL,
    salon_id BIGINT NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (evento_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    FOREIGN KEY (salon_id) REFERENCES salon(salon_id)
    );
