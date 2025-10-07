import 'package:flutter/material.dart';
import '../models/reserva_model.dart';

class ReservaAppState extends ChangeNotifier {
  // Este estado puede ser útil para compartir datos entre páginas
  // aunque tu ReservaListPage ya maneja su propio estado
  
  List<Reserva> reservasCache = [];
  
  void actualizarCache(List<Reserva> nuevasReservas) {
    reservasCache = nuevasReservas;
    notifyListeners();
  }
}