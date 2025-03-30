import 'dart:ffi';
import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/post/ClasePostDTO.dart';
import 'package:frontend/models/put/ClasePutDTO.dart';

class ApiClase {
  final ApiService _apiService;

  ApiClase({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas las clases
  Future<List<ClaseGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/clases');
    return (response as List)
        .map((json) => ClaseGetDTO.fromJson(json))
        .toList();
  }

  // Obtener una inscripcion por ID
  Future<ClaseGetDTO> obtenerPorId(Long id) async {
    final response = await _apiService.get('/api/clases/$id');
    return ClaseGetDTO.fromJson(response);
  }

  // Obtener clases por instructor
  Future<List<ClaseGetDTO>> obtenerPorInstructor(Long id) async {
    final response = await _apiService.get('/api/clases/instructor/$id');
    return (response as List)
        .map((json) => ClaseGetDTO.fromJson(json))
        .toList();
  }

  // Crear una nueva clase
  Future<ClaseGetDTO> crearClase(ClasePostDTO clase) async {
    try {
      final response = await _apiService.post(
        '/api/clases',
        clase.toJson(),
        requiresAuth: true,
      );

      return ClaseGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la clase: ${e.toString()}');
    }
  }

  // Actualizar una clase
  Future<ClaseGetDTO> actualizarClase(Long id, ClasePutDTO clase) async {
    final response = await _apiService.put('/api/clases/$id', clase.toJson());
    return ClaseGetDTO.fromJson(response);
  }

  // Confirmar asistencia
  Future<void> confirmarAsistencia(Long id) async {
    await _apiService.put('/api/clases/confirmar/$id', {});
  }

  //Desactivar clase
  Future<void> desactivarClase(Long id) async {
    await _apiService.put('/api/clases/desactivar/$id', {});
  }

  //Borrar clase
  Future<void> borrarClase(Long id) async {
    await _apiService.delete('/api/clases/$id');
  }
}
