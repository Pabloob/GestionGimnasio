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

  // Obtener todas las clases activas
  Future<List<ClaseGetDTO>> obtenerActivas() async {
    final response = await _apiService.get('/api/clases/activas');
    return (response as List)
        .map((json) => ClaseGetDTO.fromJson(json))
        .toList();
  }

  // Obtener una clase por ID
  Future<ClaseGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/clases/$id');
    return ClaseGetDTO.fromJson(response);
  }

  // Obtener cupos disponibles de una clase
  Future<int> obtenerCuposDisponibles(int id) async {
    final response = await _apiService.get('/api/clases/$id/cupos-disponibles');
    return response;
  }

  // Obtener una clase por ID
  Future<bool> tieneCuposDisponibles(int id) async {
    final response = await _apiService.get('/api/clases/$id/tiene-cupos');
    return response;
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
  Future<ClaseGetDTO> actualizarClase(int id, ClasePutDTO clase) async {
    final response = await _apiService.put('/api/clases/$id', clase.toJson());
    return ClaseGetDTO.fromJson(response);
  }

  //Borrar clase
  Future<void> borrarClase(int id) async {
    await _apiService.delete('/api/clases/$id');
  }

  //Borrar clase
  Future<void> toggleClaseStatus(int id) async {
    await _apiService.patch('/api/clases/$id/toggle-status');
  }
}
