import 'package:flutter/material.dart';
import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_inscripcion.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Clase.dart';
import 'package:frontend/models/Inscripcion.dart';
import 'package:intl/intl.dart';
import '../utils/utils.dart';

class ClientHomePage extends StatefulWidget {
  const ClientHomePage({super.key});

  @override
  State<ClientHomePage> createState() => _ClientHomePageState();
}

class _ClientHomePageState extends State<ClientHomePage> {
  dynamic usuario;

  @override
  void initState() {
    super.initState();
    _cargarUsuario();
  }

  Future<void> _cargarUsuario() async {
    final usuarioGuardado = await obtenerUsuarioGuardado();
    setState(() {
      usuario = usuarioGuardado;
    });
  }

  final ApiInscripcion inscripcionService = ApiInscripcion(
    apiService: ApiService(),
  );


  final ApiClase clasesService = ApiClase(apiService: ApiService());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              LayoutBuilder(
                builder: (context, constraints) {
                  if (constraints.maxWidth > 600) {
                    return IconButton(
                      icon: const Icon(Icons.arrow_back),
                      onPressed: () {
                        Navigator.pop(context);
                      },
                    );
                  }
                  return SizedBox.shrink();
                },
              ),

              Padding(
                padding: const EdgeInsets.only(top: 50),
                child: Row(
                  children: [
                    Image.asset("assets/icons/icon.png", height: 60),
                    const SizedBox(width: 20),
                    Expanded(
                      child: Text(
                        "Bienvenido ${usuario?.nombre ?? 'Usuario'}",
                        style: const TextStyle(
                          fontSize: 32,
                          fontWeight: FontWeight.bold,
                          color: Color(0xfffa6045),
                        ),
                        overflow: TextOverflow.ellipsis,
                        maxLines: 1,
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 60),
              const SizedBox(height: 30),
              // Tabla de clases del día
              Padding(
                padding: const EdgeInsets.only(left: 8),
                child: const Text(
                  "Clases de hoy",
                  style: TextStyle(
                    fontSize: 13,
                    color: Colors.black54,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
              const SizedBox(height: 5),
              // Tabla con datos
              FutureBuilder<List<Clase>>(
                future: obtenerClases(),
                // Llamamos a la función para obtener las clases
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(child: CircularProgressIndicator());
                  } else if (snapshot.hasError) {
                    return Center(child: Text('Error: ${snapshot.error}'));
                  } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return const Center(
                      child: Text('No hay clases disponibles.'),
                    );
                  } else {
                    List<Clase> clases = snapshot.data!;

                    return Table(
                      border: TableBorder.all(color: Colors.black54, width: 1.0),
                      defaultVerticalAlignment: TableCellVerticalAlignment.middle,
                      columnWidths: {
                        0: FlexColumnWidth(2.0), // Ajustar el ancho de las columnas
                        1: FlexColumnWidth(1.0),
                        2: FlexColumnWidth(1.5),
                        3: FlexColumnWidth(1.5),
                        4: FlexColumnWidth(1.5),
                      },
                      children: [
                        // Encabezados de la tabla
                        TableRow(
                          decoration: BoxDecoration(
                            color: Colors.grey[200], // Sombra de color para los encabezados
                          ),
                          children: [
                            _buildTableCell('Nombre', isHeader: true),
                            _buildTableCell('Sala', isHeader: true),
                            _buildTableCell('Hora inicio', isHeader: true),
                            _buildTableCell('Hora fin', isHeader: true),
                            _buildTableCell('Apuntados', isHeader: true),
                          ],
                        ),
                        // Filas de las clases
                        for (var clase in clases)
                          TableRow(
                            decoration: BoxDecoration(
                              color: (clases.indexOf(clase) % 2 == 0)
                                  ? Colors.grey[100]
                                  : Colors.transparent,
                            ),
                            children: [
                              _buildTableCell(clase.nombre),
                              _buildTableCell(clase.sala),
                              _buildTableCell(DateFormat("HH:mm").format(clase.horaInicio)),
                              _buildTableCell(DateFormat("HH:mm").format(clase.horaFin)),
                              _buildTableCell("${clases.length} / ${clase.capacidadMaxima}"),
                            ],
                          ),
                      ],
                    );
                  }
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTableCell(String text, {bool isHeader = false}) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Text(
        text,
        style: TextStyle(
          fontWeight: isHeader ? FontWeight.bold : FontWeight.normal,
          fontSize: 14,
          color: Colors.black,
        ),
        textAlign: TextAlign.center,
      ),
    );
  }

  // Función para obtener las clases, usando el userId del token
  Future<List<Clase>> obtenerClases() async {
    int? userId = await obtenerUserIdDesdeToken();

    if (userId != null) {
      // Obtener todas las inscripciones del usuario
      final List<Inscripcion> inscripciones = await inscripcionService
          .obtenerPorUsuario(userId);

      // Obtener todas las clases asociadas a las inscripciones
      List<Clase> clases = await Future.wait(
        inscripciones.map(
          (inscripcion) async =>
              await clasesService.obtenerPorId(inscripcion.idClase),
        ),
      );

      return clases;
    } else {
      return [];
    }
  }
}
