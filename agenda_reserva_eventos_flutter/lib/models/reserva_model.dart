import 'package:flutter/material.dart';

class Reserva {
  final int? id;
  final String fechaInicio;
  final String fechaFin;
  final String estado;
  final Map<String, dynamic> usuario;
  final Map<String, dynamic> salon;
  final Map<String, dynamic> evento;

  Reserva({
    this.id,
    required this.fechaInicio,
    required this.fechaFin,
    required this.estado,
    required this.usuario,
    required this.salon,
    required this.evento,
  });

  factory Reserva.fromJson(Map<String, dynamic> json) {
    return Reserva(
      id: json['id'],
      fechaInicio: json['fechaInicio'],
      fechaFin: json['fechaFin'],
      estado: json['estado'],
      usuario: json['usuario'] is Map ? json['usuario'] : {'id': json['usuario']},
      salon: json['salon'] is Map ? json['salon'] : {'id': json['salon']},
      evento: json['evento'] is Map ? json['evento'] : {'id': json['evento']},
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'fechaInicio': fechaInicio,
      'fechaFin': fechaFin,
      'estado': estado,
      'usuario': usuario,
      'salon': salon,
      'evento': evento,
    };
  }

  // Método para mostrar en la UI
  String get fechaFormateada {
    try {
      final fecha = DateTime.parse(fechaInicio);
      return '${fecha.day}/${fecha.month}/${fecha.year} ${fecha.hour}:${fecha.minute.toString().padLeft(2, '0')}';
    } catch (e) {
      return fechaInicio;
    }
  }

  Color get colorEstado {
    switch (estado) {
      case 'CONFIRMADA':
        return Colors.green;
      case 'CANCELADA':
        return Colors.red;
      case 'PENDIENTE':
        return Colors.orange;
      default:
        return Colors.grey;
    }
  }
}