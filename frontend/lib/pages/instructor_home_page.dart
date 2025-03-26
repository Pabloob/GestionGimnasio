import 'package:flutter/material.dart';

class InstructorHomePage extends StatefulWidget {
  const InstructorHomePage({super.key});

  @override
  State<InstructorHomePage> createState() => _InstructorHomePageState();
}

class _InstructorHomePageState extends State<InstructorHomePage> {
  // Datos de ejemplo para las clases
  final List<Map<String, String>> clases = [
    {
      'nombre': 'Pilates',
      'sala': 'Sala 1',
      'hora': '10:00 - 11:00',
      'apuntados': '15',
    },
    {
      'nombre': 'Yoga',
      'sala': 'Sala 2',
      'hora': '11:30 - 12:30',
      'apuntados': '12',
    },
    {
      'nombre': 'Zumba',
      'sala': 'Sala 3',
      'hora': '13:00 - 14:00',
      'apuntados': '20',
    },
  ];

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
              // Solo mostrar el IconButton en pantallas grandes (escritorio)
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
                    const Text(
                      "Bienvenido nombre",
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                        color: Color(0xfffa6045),
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
              Table(
                border: TableBorder.all(color: Colors.black54),
                children: [
                  // Encabezados de la tabla
                  TableRow(
                    children: [
                      _buildTableCell('Nombre', isHeader: true),
                      _buildTableCell('Sala', isHeader: true),
                      _buildTableCell('Hora', isHeader: true),
                      _buildTableCell('Apuntados', isHeader: true),
                    ],
                  ),
                  // Filas de las clases
                  for (var clase in clases)
                    TableRow(
                      children: [
                        _buildTableCell(clase['nombre']!),
                        _buildTableCell(clase['sala']!),
                        _buildTableCell(clase['hora']!),
                        _buildTableCell(clase['apuntados']!),
                      ],
                    ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  // Método para crear celdas de la tabla
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
}
