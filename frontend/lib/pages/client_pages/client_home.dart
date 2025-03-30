import 'package:flutter/material.dart';
import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_cliente.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/services/clase_service.dart';
import 'package:frontend/utils/common_widgets.dart';
import 'package:frontend/utils/utils.dart';
import 'package:intl/intl.dart';

class ClientHomePage extends StatefulWidget {
  const ClientHomePage({super.key});

  @override
  State<ClientHomePage> createState() => _ClientHomePageState();
}

class _ClientHomePageState extends State<ClientHomePage> {
  dynamic _user;
  late final ClaseService _classService;
  late Future<List<ClaseGetDTO>> _classesFuture;

  @override
  void initState() {
    super.initState();
    _initializeServices();
    _classesFuture = _loadUserAndClasses();
  }

  void _initializeServices() {
    _classService = ClaseService(
      claseService: ApiClase(apiService: ApiService()),
      clienteService: ApiCliente(apiService: ApiService()),
    );
  }

  Future<List<ClaseGetDTO>> _loadUserAndClasses() async {
    _user = await obtenerUsuarioGuardado();
    if (mounted) setState(() {});
    return _classService.obtenerClases();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          CommonWidgets.buildCustomTopMesage(
            user: _user,
            textoPrincipal: "Bienvenido",
            textoSecundario: "¿Listo para entrenar?",
          ),

          // Sección de clases
          Padding(
            padding: const EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Clases disponibles",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                ),
                SizedBox(height: 10),
                FutureBuilder<List<ClaseGetDTO>>(
                  future: _classesFuture,
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.waiting) {
                      return Center(child: CircularProgressIndicator());
                    } else if (snapshot.hasError) {
                      return Center(child: Text('Error: ${snapshot.error}'));
                    } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                      return Center(child: Text('No hay clases disponibles.'));
                    }
                    return ListView.builder(
                      shrinkWrap: true,
                      physics: NeverScrollableScrollPhysics(),
                      itemCount: snapshot.data!.length,
                      itemBuilder: (context, index) {
                        final clase = snapshot.data![index];
                        return _buildClassCard(clase);
                      },
                    );
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildClassCard(ClaseGetDTO clase) {
    return Card(
      elevation: 3,
      margin: EdgeInsets.symmetric(vertical: 8),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: ListTile(
        contentPadding: EdgeInsets.all(16),
        leading: CircleAvatar(
          backgroundColor: Color(0xFFB7C4B5),
          child: Icon(Icons.fitness_center, color: Colors.white),
        ),
        title: Text(
          clase.nombre!,
          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text("Días: ${clase.getDias()!}"),
            Text(
              "Horario: ${DateFormat("HH:mm").format(clase.horaInicio!)} - ${DateFormat("HH:mm").format(clase.horaFin!)}",
            ),
            Text("Sala: ${clase.sala}"),
          ],
        ),
        trailing: ElevatedButton(
          onPressed: () {},
          style: ElevatedButton.styleFrom(
            backgroundColor: Color(0xFF7D8C88), // Color principal
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
          ),
          child: Icon(Icons.notifications, color: Colors.white),
        ),
      ),
    );
  }
}
