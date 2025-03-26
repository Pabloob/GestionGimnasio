import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Clase.dart';

class ApiClase {
  final ApiService _apiService;

  ApiClase({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas las clases
  Future<List<Clase>> obtenerTodos() async {
    final response = await _apiService.get('/api/clases');
    return (response as List).map((json) => Clase.fromJson(json)).toList();
  }

  // Obtener una inscripcion por ID
  Future<Clase> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/clases/$id');
    return Clase.fromJson(response);
  }

  // Crear una nueva clase
  Future<Clase> crearClase(Clase clase) async {
    try {
      final response = await _apiService.post(
        '/api/clases',
        clase.toJson(),
        requiresAuth: false,
      );

      return Clase.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la clase: ${e.toString()}');
    }
  }

  // Actualizar una clase
  Future<Clase> actualizarClase(int id, Clase clase) async {
    final response = await _apiService.put('/api/clases/$id', clase.toJson());
    return Clase.fromJson(response);
  }

  // Eliminar una clase
  Future<void> eliminarInscripcion(int id) async {
    await _apiService.delete('/api/clases/$id');
  }

  //Obtener clases de un instructor
  Future<List<Clase>> obtenerPorInstructor(int id) async {
    final response = await _apiService.get('/api/clases/instructor/$id');
    return (response as List).map((json) => Clase.fromJson(json)).toList();
  }

  //Obtener clase por nombre
  Future<List<Clase>> obtenerPorNombre(String nombre) async {
    final response = await _apiService.get('/api/clases/search/$nombre');
    return (response as List).map((json) => Clase.fromJson(json)).toList();
  }

  Future<Clase> cambiarEstado(int id, bool activo) async {
    final response = await _apiService.put('/api/clases/$id/estado', {
      'activa': activo.toString(),
    });
    return response;
  }
}
