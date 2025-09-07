#!/bin/bash

# Variables de conexión
DB_NAME="agenda_eventos"
DB_USER="root"
DB_PASS="tu_contraseña"

# Insertar salones
mysql -u $DB_USER -p$DB_PASS $DB_NAME <<EOF
INSERT INTO salon (nombre, capacidad, ubicacion, caracteristicas) VALUES
('Salon A', 50, 'Primer piso', 'Proyector, Sonido'),
('Salon B', 100, 'Segundo piso', 'WiFi, Aire acondicionado');
EOF

# Insertar eventos
mysql -u $DB_USER -p$DB_PASS $DB_NAME <<EOF
INSERT INTO evento (tipo_evento, fecha, hora, salon_id) VALUES
('Cumpleaños', '2025-09-10', '15:00', 1),
('Reunión', '2025-09-12', '10:00', 2);
EOF

# Insertar reservas
mysql -u $DB_USER -p$DB_PASS $DB_NAME <<EOF
INSERT INTO reserva (usuario_id, evento_id, requisitos) VALUES
(1, 1, 'Pastel y decoración'),
(2, 2, 'Proyector y micrófono');
EOF

# Insertar usuarios
mysql -u $DB_USER -p$DB_PASS $DB_NAME <<EOF
INSERT INTO usuario (nombre, correo, rol) VALUES
('Ismael Yate', 'iyate@example.com', 'Admin'),
('Paola Mambuscay', 'pmambuscay@example.com', 'Usuario');
EOF

echo "Datos de prueba cargados correctamente."
