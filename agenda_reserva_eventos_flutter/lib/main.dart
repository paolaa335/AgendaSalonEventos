import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'pages/reservas/reserva_list_page.dart';
import 'pages/reservas/reserva_form_page.dart';
import 'services/reserva_app_state.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => ReservaAppState(),
      child: MaterialApp(
        title: 'Sistema de Reservas de Salones',
        theme: ThemeData(
          useMaterial3: true,
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        ),
        home: MyHomePage(),
        routes: {
          '/reservas': (context) => ReservaListPage(),
          '/nueva-reserva': (context) => ReservaFormPage(),
        },
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  var selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    bool isWide = MediaQuery.of(context).size.width > 600;

    Widget page;
    switch (selectedIndex) {
      case 0:
        page = ReservaListPage(); // Tu página existente
        break;
      case 1:
        page = ReservaFormPage(); // Tu formulario existente
        break;
      default:
        throw UnimplementedError('no widget for $selectedIndex');
    }

    return Scaffold(
      appBar: AppBar(
        title: Text('Sistema de Reservas de Salones'),
        backgroundColor: Theme.of(context).colorScheme.primary,
        foregroundColor: Colors.white,
      ),
      body: Row(
        children: [
          SafeArea(
            child: NavigationRail(
              extended: isWide,
              destinations: [
                NavigationRailDestination(
                  icon: Icon(Icons.list),
                  label: Text('Ver Reservas'),
                ),
                NavigationRailDestination(
                  icon: Icon(Icons.add),
                  label: Text('Nueva Reserva'),
                ),
              ],
              selectedIndex: selectedIndex,
              onDestinationSelected: (value) {
                setState(() {
                  selectedIndex = value;
                });
              },
            ),
          ),
          Expanded(
            child: Container(
              color: Theme.of(context).colorScheme.background,
              child: page,
            ),
          ),
        ],
      ),
    );
  }
}