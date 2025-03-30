import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_cliente.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/utils/utils.dart';

class ClaseService {
  final ApiClase _claseService;
  final ApiCliente _clienteService;

  ClaseService({required ApiClase claseService, required ApiCliente clienteService})
      : _claseService = claseService,
        _clienteService = clienteService;

  // Obtener clases disponibles para un usuario
  Future<List<ClaseGetDTO>> obtenerClasesDisponibles() async {
    final userId = await obtenerUserIdDesdeToken();
    if (userId == null) return [];

    final allClasses = await _claseService.obtenerTodos();
    final enrolledClasses = await _clienteService.obtenerClases(userId);
    
    return allClasses.where(
      (clase) => !enrolledClasses.any((c) => c.nombre == clase.nombre)
    ).toList();
  }

  // Obtener clases del usuario
  Future<List<ClaseGetDTO>> obtenerClases() async {
    final userId = await obtenerUserIdDesdeToken();
    if (userId == null) return [];
    return await _clienteService.obtenerClases(userId);
  }
}