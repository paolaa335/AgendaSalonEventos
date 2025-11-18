import 'package:flutter/material.dart';
import '../../models/reserva_model.dart';
import '../../services/reserva_service.dart';

class ReservaFormPage extends StatefulWidget {
  const ReservaFormPage({super.key});

  @override
  State<ReservaFormPage> createState() => _ReservaFormPageState();
}

class _ReservaFormPageState extends State<ReservaFormPage> {
  final _formKey = GlobalKey<FormState>();
  
  // Controladores para los campos del formulario
  final _fechaInicioController = TextEditingController();
  final _fechaFinController = TextEditingController();
  final _usuarioIdController = TextEditingController(text: '1');
  final _salonIdController = TextEditingController(text: '1');
  final _eventoIdController = TextEditingController(text: '1');
  
  String _estado = 'PENDIENTE';
  bool _isLoading = false;

  @override
  void dispose() {
    _fechaInicioController.dispose();
    _fechaFinController.dispose();
    _usuarioIdController.dispose();
    _salonIdController.dispose();
    _eventoIdController.dispose();
    super.dispose();
  }

  Future<void> _crearReserva() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _isLoading = true;
    });

    try {
      final nuevaReserva = Reserva(
        fechaInicio: _fechaInicioController.text,
        fechaFin: _fechaFinController.text,
        estado: _estado,
        usuario: {'id': int.parse(_usuarioIdController.text)},
        salon: {'id': int.parse(_salonIdController.text)},
        evento: {'id': int.parse(_eventoIdController.text)},
      );

      await ReservaService.createReserva(nuevaReserva);
      
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Reserva creada exitosamente'),
            backgroundColor: Colors.green,
          ),
        );
        Navigator.of(context).pop();
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error al crear reserva: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  void _seleccionarFechaHora(bool isInicio) {
    showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 365)),
    ).then((fecha) {
      if (fecha != null) {
        showTimePicker(
          context: context,
          initialTime: TimeOfDay.now(),
        ).then((hora) {
          if (hora != null) {
            final fechaHora = DateTime(
              fecha.year,
              fecha.month,
              fecha.day,
              hora.hour,
              hora.minute,
            );
            final texto = fechaHora.toIso8601String();
            if (isInicio) {
              _fechaInicioController.text = texto;
            } else {
              _fechaFinController.text = texto;
            }
          }
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Nueva Reserva'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : Padding(
              padding: const EdgeInsets.all(16.0),
              child: Form(
                key: _formKey,
                child: ListView(
                  children: [
                    // Campo Fecha Inicio
                    TextFormField(
                      controller: _fechaInicioController,
                      decoration: InputDecoration(
                        labelText: 'Fecha y Hora de Inicio',
                        suffixIcon: IconButton(
                          icon: const Icon(Icons.calendar_today),
                          onPressed: () => _seleccionarFechaHora(true),
                        ),
                      ),
                      readOnly: true,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Por favor selecciona la fecha de inicio';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),

                    // Campo Fecha Fin
                    TextFormField(
                      controller: _fechaFinController,
                      decoration: InputDecoration(
                        labelText: 'Fecha y Hora de Fin',
                        suffixIcon: IconButton(
                          icon: const Icon(Icons.calendar_today),
                          onPressed: () => _seleccionarFechaHora(false),
                        ),
                      ),
                      readOnly: true,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Por favor selecciona la fecha de fin';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),

                    // Campo Usuario ID
                    TextFormField(
                      controller: _usuarioIdController,
                      decoration: const InputDecoration(
                        labelText: 'ID del Usuario',
                      ),
                      keyboardType: TextInputType.number,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Por favor ingresa el ID del usuario';
                        }
                        if (int.tryParse(value) == null) {
                          return 'Por favor ingresa un número válido';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),

                    // Campo Salón ID
                    TextFormField(
                      controller: _salonIdController,
                      decoration: const InputDecoration(
                        labelText: 'ID del Salón',
                      ),
                      keyboardType: TextInputType.number,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Por favor ingresa el ID del salón';
                        }
                        if (int.tryParse(value) == null) {
                          return 'Por favor ingresa un número válido';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),

                    // Campo Evento ID
                    TextFormField(
                      controller: _eventoIdController,
                      decoration: const InputDecoration(
                        labelText: 'ID del Evento',
                      ),
                      keyboardType: TextInputType.number,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Por favor ingresa el ID del evento';
                        }
                        if (int.tryParse(value) == null) {
                          return 'Por favor ingresa un número válido';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),

                    // Selector de Estado
                    DropdownButtonFormField<String>(
                      value: _estado,
                      decoration: const InputDecoration(
                        labelText: 'Estado de la Reserva',
                      ),
                      items: ['PENDIENTE', 'CONFIRMADA', 'CANCELADA']
                          .map((estado) => DropdownMenuItem(
                                value: estado,
                                child: Text(estado),
                              ))
                          .toList(),
                      onChanged: (value) {
                        setState(() {
                          _estado = value!;
                        });
                      },
                    ),
                    const SizedBox(height: 24),

                    // Botón de enviar
                    ElevatedButton(
                      onPressed: _isLoading ? null : _crearReserva,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.blue,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 16),
                      ),
                      child: _isLoading
                          ? const SizedBox(
                              height: 20,
                              width: 20,
                              child: CircularProgressIndicator(
                                strokeWidth: 2,
                                valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                              ),
                            )
                          : const Text(
                              'Crear Reserva',
                              style: TextStyle(fontSize: 16),
                            ),
                    ),
                  ],
                ),
              ),
            ),
    );
  }
}