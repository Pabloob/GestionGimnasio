import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Inscripcion.dart';

class ApiInscripcion {
  final ApiService _apiService;

  ApiInscripcion({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas las inscripciones
  Future<List<Inscripcion>> obtenerTodos() async {
    final response = await _apiService.get('/api/inscripciones');
    return (response as List)
        .map((json) => Inscripcion.fromJson(json))
        .toList();
  }

  // Obtener una inscripcion por ID
  Future<Inscripcion> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/inscripciones/$id');
    return Inscripcion.fromJson(response);
  }

  // Crear una nueva inscripcion
  Future<Inscripcion> crearInscripcion(Inscripcion inscripcion) async {
    try {
      final response = await _apiService.post(
        '/api/inscripciones',
        inscripcion.toJson(),
        requiresAuth: false,
      );

      return Inscripcion.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la inscripcion: ${e.toString()}');
    }
  }

  // Actualizar una inscripcion
  Future<Inscripcion> actualizarInscripcion(
    int id,
    Inscripcion inscripcion,
  ) async {
    final response = await _apiService.put(
      '/api/inscripciones/$id',
      inscripcion.toJson(),
    );
    return Inscripcion.fromJson(response);
  }

  // Eliminar una inscripcion
  Future<void> eliminarInscripcion(int id) async {
    await _apiService.delete('/api/inscripciones/$id');
  }

  //Obtener inscripciones de un usuario
  Future<List<Inscripcion>> obtenerPorUsuario(int id) async {
    final response = await _apiService.get('/api/inscripciones/cliente/$id');
    return (response as List)
        .map((json) => Inscripcion.fromJson(json))
        .toList();
  }

  //Obtener inscripciones de una clase
  Future<List<Inscripcion>> obtenerPorClase(int id) async {
    final response = await _apiService.get('/api/inscripciones/clase/$id');
    return (response as List)
        .map((json) => Inscripcion.fromJson(json))
        .toList();
  }

  Future<Inscripcion> pagarClase(int id, bool pagado) async {
    final response = await _apiService.put('/api/inscripciones/$id/pago', {
      'pagado': pagado.toString(),
    });
    return response;
  }
}
