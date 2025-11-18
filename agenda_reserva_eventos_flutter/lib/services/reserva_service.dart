import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/reserva_model.dart';

class ReservaService {
  static const String baseUrl = 'http://localhost:8081/reserva';

  // Headers comunes
  static final Map<String, String> headers = {
    'Content-Type': 'application/json',
  };

  // GET /reserva/all - Obtener todas las reservas
  static Future<List<Reserva>> getReservas() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/all'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        return jsonList.map((json) => Reserva.fromJson(json)).toList();
      } else {
        throw Exception('Error al cargar reservas: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // POST /reserva/save - Crear nueva reserva
  static Future<Reserva> createReserva(Reserva reserva) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/save'),
        headers: headers,
        body: json.encode(reserva.toJson()),
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonResponse = json.decode(response.body);
        return Reserva.fromJson(jsonResponse);
      } else {
        throw Exception('Error al crear reserva: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // PUT /reserva/cancelar - Cancelar reserva
  static Future<Reserva> cancelarReserva(int id) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/cancelar?id=$id'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonResponse = json.decode(response.body);
        return Reserva.fromJson(jsonResponse);
      } else {
        throw Exception('Error al cancelar reserva: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // PUT /reserva/confirmar - Confirmar reserva
  static Future<Reserva> confirmarReserva(int id) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/confirmar?id=$id'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonResponse = json.decode(response.body);
        return Reserva.fromJson(jsonResponse);
      } else {
        throw Exception('Error al confirmar reserva: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // DELETE /reserva/delete - Eliminar reserva
  static Future<void> deleteReserva(int id) async {
    try {
      final response = await http.delete(
        Uri.parse('$baseUrl/delete?id=$id'),
        headers: headers,
      );

      if (response.statusCode != 200) {
        throw Exception('Error al eliminar reserva: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }
}