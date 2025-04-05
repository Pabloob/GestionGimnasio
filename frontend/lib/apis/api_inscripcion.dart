import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/InscripcionGetDTO.dart';
import 'package:frontend/models/post/InscripcionPostDTO.dart';
import 'package:frontend/models/put/InscripcionPutDTO.dart';

class ApiInscripcion {
  final ApiService _apiService;

  ApiInscripcion({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas las inscripciones
  Future<List<InscripcionGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/inscripciones');
    return (response as List)
        .map((json) => InscripcionGetDTO.fromJson(json))
        .toList();
  }

  // Obtener una inscripcion por ID
  Future<InscripcionGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/inscripciones/$id');
    return InscripcionGetDTO.fromJson(response);
  }

  // Obtener inscripciones por cliente
  Future<List<InscripcionGetDTO>> obtenerPorCliente(int id) async {
    final response = await _apiService.get('/api/inscripciones/cliente/$id');
    return (response as List)
        .map((json) => InscripcionGetDTO.fromJson(json))
        .toList();
  }

  // Obtener inscripciones por clase
  Future<List<InscripcionGetDTO>> obtenerPorClase(int id) async {
    final response = await _apiService.get('/api/inscripciones/clase/$id');
    return (response as List)
        .map((json) => InscripcionGetDTO.fromJson(json))
        .toList();
  }

  // Crear una nueva inscripcion
  Future<InscripcionGetDTO> crearInscripcion(
    InscripcionPostDTO inscripcion,
  ) async {
    try {
      final response = await _apiService.post(
        '/api/inscripciones',
        inscripcion.toJson(),
        requiresAuth: true,
      );

      return InscripcionGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la inscripcion: ${e.toString()}');
    }
  }

  // Actualizar una inscripcion
  Future<InscripcionGetDTO> actualizarInscripcion(
    int id,
    InscripcionPutDTO inscripcion,
  ) async {
    final response = await _apiService.put(
      '/api/inscripciones/$id',
      inscripcion.toJson(),
    );
    return InscripcionGetDTO.fromJson(response);
  }

  // Eliminar una inscripcion
  Future<void> eliminarInscripcion(int id) async {
    await _apiService.delete('/api/inscripciones/$id');
  }

  // Confirmar asistencia
  Future<void> registrarAsistencia(int id) async {
    await _apiService.patch('/api/inscripciones/$id/registrar-asistencia');
  }
}
