import 'package:flutter/material.dart';
import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_cliente.dart';
import 'package:frontend/apis/api_inscripcion.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/services/clase_service.dart';
import 'package:frontend/services/inscripcion_service.dart';
import 'package:frontend/utils/common_widgets.dart';
import 'package:frontend/utils/utils.dart';
import 'package:intl/intl.dart';

class ClienteAddPage extends StatefulWidget {
  final VoidCallback? onEnrollmentSuccess;
  const ClienteAddPage({super.key, this.onEnrollmentSuccess});

  @override
  State<ClienteAddPage> createState() => _ClienteAddPageState();
}

class _ClienteAddPageState extends State<ClienteAddPage> {
  dynamic _user;
  final List<ClaseGetDTO> _selectedClasses = [];
  late final ClaseService _classService;
  late final InscripcionService _enrollmentService;
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
    _enrollmentService = InscripcionService(
      inscripcionService: ApiInscripcion(apiService: ApiService()),
    );
  }

  Future<List<ClaseGetDTO>> _loadUserAndClasses() async {
    _user = await obtenerUsuarioGuardado();
    if (mounted) setState(() {});
    return _classService.obtenerClasesDisponibles();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          CommonWidgets.buildCustomTopMesage(
            user: _user,
            textoPrincipal: "Bienvenido",
            textoSecundario: "Hoy es un gran día para entrenar",
          ),

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

                SizedBox(
                  height: 20,
                  child:
                      _selectedClasses.isNotEmpty
                          ? ElevatedButton(
                            onPressed: _showConfirmationDialog,
                            style: ElevatedButton.styleFrom(
                              backgroundColor: Color(
                                0xFF34C759,
                              ), // Verde vibrante
                              shadowColor: Colors.transparent,
                              foregroundColor: Colors.white,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(8),
                              ),
                            ),
                            child: const Text("Confirmar"),
                          )
                          : SizedBox(),
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

  void _toggleClassSelection(ClaseGetDTO clase) {
    setState(() {
      _selectedClasses.contains(clase)
          ? _selectedClasses.remove(clase)
          : _selectedClasses.add(clase);
    });
  }

  Future<void> _handleEnrollment() async {
    Navigator.of(context).pop();

    final success = await _enrollmentService.inscribir(_selectedClasses);
    if (!mounted) return;

    if (success) {
      // Llamamos al callback si existe
      widget.onEnrollmentSuccess?.call();

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Inscripción completada con éxito'),
          backgroundColor: Colors.green,
        ),
      );
      setState(() {
        _selectedClasses.clear();
        _classesFuture = _classService.obtenerClasesDisponibles();
      });
    }
  }

  void _showConfirmationDialog() {
    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            backgroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(16),
            ),
            title: Text(
              "Confirmar Inscripción",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Color(0xFF0057FF),
              ),
            ),
            content: SingleChildScrollView(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "Las siguientes clases serán reservadas para ti:",
                    style: TextStyle(fontSize: 14, color: Colors.black87),
                  ),
                  SizedBox(height: 10),
                  Table(
                    children: [
                      ..._selectedClasses.map(
                        (clase) => TableRow(
                          children: [
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 8),
                              child: Text(
                                clase.nombre!,
                                style: TextStyle(
                                  fontSize: 14,
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 8),
                              child: Text(
                                "${clase.precio.toString()} €",
                                style: TextStyle(
                                  fontSize: 14,
                                  color: Color(0xFF0057FF),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                      TableRow(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 8),
                            child: Text(
                              "Total",
                              style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                                color: Colors.black87,
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 8),
                            child: Text(
                              "${_selectedClasses.fold(0.0, (sum, clase) => sum + clase.precio!)} €",
                              style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                                color: Color(0xFF0057FF),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                  SizedBox(height: 20),
                  Text(
                    "¿Quieres confirmar tu inscripción?",
                    style: TextStyle(fontSize: 14, color: Colors.black54),
                  ),
                ],
              ),
            ),
            actions: [
              TextButton(
                onPressed: _handleEnrollment,
                style: TextButton.styleFrom(
                  backgroundColor: Color(0xFF0057FF),
                  padding: EdgeInsets.symmetric(horizontal: 30, vertical: 12),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: Text(
                  "Confirmar",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: Text(
                  "Cerrar",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.black54,
                  ),
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
          backgroundColor: Color(0xFFFF9500), // Naranja vibrante
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
            Text("Precio: ${clase.precio}€"),
          ],
        ),
        trailing: ElevatedButton(
          onPressed: () {
            _toggleClassSelection(clase);
          },
          style: ElevatedButton.styleFrom(
            backgroundColor:
                _selectedClasses.contains(clase)
                    ? Colors.redAccent
                    : Color(0xFF007AFF),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
          ),
          child: Icon(
            _selectedClasses.contains(clase)
                ? Icons.highlight_remove
                : Icons.add_circle_outline,
            color: Colors.white,
          ),
        ),
      ),
    );
  }
}
