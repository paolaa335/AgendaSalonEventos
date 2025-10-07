import 'package:flutter/material.dart';
import '../../models/reserva_model.dart';
import '../../services/reserva_service.dart';
import 'reserva_form_page.dart';

class ReservaListPage extends StatefulWidget {
  const ReservaListPage({super.key});

  @override
  State<ReservaListPage> createState() => _ReservaListPageState();
}

class _ReservaListPageState extends State<ReservaListPage> {
  List<Reserva> _reservas = [];
  bool _isLoading = true;
  String _errorMessage = '';

  @override
  void initState() {
    super.initState();
    _cargarReservas();
  }

  Future<void> _cargarReservas() async {
    try {
      setState(() {
        _isLoading = true;
        _errorMessage = '';
      });

      final reservas = await ReservaService.getReservas();
      
      setState(() {
        _reservas = reservas;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
        _errorMessage = e.toString();
      });
      _mostrarError(e.toString());
    }
  }

  void _mostrarError(String mensaje) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text('Error: $mensaje'),
        backgroundColor: Colors.red,
      ),
    );
  }

  void _mostrarConfirmacionEliminar(Reserva reserva) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Confirmar eliminación'),
          content: Text('¿Estás seguro de eliminar la reserva #${reserva.id}?'),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancelar'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                _eliminarReserva(reserva.id!);
              },
              child: const Text('Eliminar', style: TextStyle(color: Colors.red)),
            ),
          ],
        );
      },
    );
  }

  Future<void> _eliminarReserva(int id) async {
    try {
      await ReservaService.deleteReserva(id);
      _mostrarMensaje('Reserva eliminada correctamente');
      _cargarReservas();
    } catch (e) {
      _mostrarError(e.toString());
    }
  }

  Future<void> _cancelarReserva(int id) async {
    try {
      await ReservaService.cancelarReserva(id);
      _mostrarMensaje('Reserva cancelada correctamente');
      _cargarReservas();
    } catch (e) {
      _mostrarError(e.toString());
    }
  }

  Future<void> _confirmarReserva(int id) async {
    try {
      await ReservaService.confirmarReserva(id);
      _mostrarMensaje('Reserva confirmada correctamente');
      _cargarReservas();
    } catch (e) {
      _mostrarError(e.toString());
    }
  }

  void _mostrarMensaje(String mensaje) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(mensaje),
        backgroundColor: Colors.green,
      ),
    );
  }

  void _navegarAFormulario() {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => ReservaFormPage()),
    ).then((_) => _cargarReservas());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gestión de Reservas'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _cargarReservas,
            tooltip: 'Recargar',
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _errorMessage.isNotEmpty
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Icon(Icons.error_outline, size: 64, color: Colors.red),
                      const SizedBox(height: 16),
                      Text(
                        'Error de conexión',
                        style: Theme.of(context).textTheme.headlineSmall,
                      ),
                      const SizedBox(height: 8),
                      Text(
                        _errorMessage,
                        textAlign: TextAlign.center,
                        style: Theme.of(context).textTheme.bodyMedium,
                      ),
                      const SizedBox(height: 16),
                      ElevatedButton(
                        onPressed: _cargarReservas,
                        child: const Text('Reintentar'),
                      ),
                    ],
                  ),
                )
              : _reservas.isEmpty
                  ? Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          const Icon(Icons.event_available, size: 64, color: Colors.grey),
                          const SizedBox(height: 16),
                          Text(
                            'No hay reservas',
                            style: Theme.of(context).textTheme.headlineSmall,
                          ),
                          const SizedBox(height: 8),
                          const Text('Presiona el botón + para crear una nueva reserva'),
                        ],
                      ),
                    )
                  : ListView.builder(
                      itemCount: _reservas.length,
                      itemBuilder: (context, index) {
                        final reserva = _reservas[index];
                        return Card(
                          margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                          child: ListTile(
                            leading: CircleAvatar(
                              backgroundColor: reserva.colorEstado,
                              child: Text(
                                '${reserva.id}',
                                style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                              ),
                            ),
                            title: Text('Reserva #${reserva.id}'),
                            subtitle: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(reserva.fechaFormateada),
                                const SizedBox(height: 4),
                                Chip(
                                  label: Text(
                                    reserva.estado,
                                    style: const TextStyle(color: Colors.white, fontSize: 12),
                                  ),
                                  backgroundColor: reserva.colorEstado,
                                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                                ),
                              ],
                            ),
                            trailing: PopupMenuButton<String>(
                              onSelected: (value) {
                                switch (value) {
                                  case 'confirmar':
                                    _confirmarReserva(reserva.id!);
                                    break;
                                  case 'cancelar':
                                    _cancelarReserva(reserva.id!);
                                    break;
                                  case 'eliminar':
                                    _mostrarConfirmacionEliminar(reserva);
                                    break;
                                }
                              },
                              itemBuilder: (BuildContext context) => [
                                if (reserva.estado == 'PENDIENTE')
                                  const PopupMenuItem<String>(
                                    value: 'confirmar',
                                    child: Row(
                                      children: [
                                        Icon(Icons.check, color: Colors.green),
                                        SizedBox(width: 8),
                                        Text('Confirmar'),
                                      ],
                                    ),
                                  ),
                                if (reserva.estado != 'CANCELADA')
                                  const PopupMenuItem<String>(
                                    value: 'cancelar',
                                    child: Row(
                                      children: [
                                        Icon(Icons.cancel, color: Colors.orange),
                                        SizedBox(width: 8),
                                        Text('Cancelar'),
                                      ],
                                    ),
                                  ),
                                const PopupMenuItem<String>(
                                  value: 'eliminar',
                                  child: Row(
                                    children: [
                                      Icon(Icons.delete, color: Colors.red),
                                      SizedBox(width: 8),
                                      Text('Eliminar'),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                        );
                      },
                    ),
      floatingActionButton: FloatingActionButton(
        onPressed: _navegarAFormulario,
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
        child: const Icon(Icons.add),
      ),
    );
  }
}