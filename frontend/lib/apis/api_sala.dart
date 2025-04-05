import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/SalaGetDTO.dart';
import 'package:frontend/models/post/SalaPostDTO.dart';
import 'package:frontend/models/put/SalaPutDTO.dart';

class ApiSala {
  final ApiService _apiService;

  ApiSala({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas las salas
  Future<List<SalaGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/salas');
    return (response as List)
        .map((json) => SalaGetDTO.fromJson(json))
        .toList();
  }

  // Obtener una sala por ID
  Future<SalaGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/salas/$id');
    return SalaGetDTO.fromJson(response);
  }

  // Crear una nueva sala
  Future<SalaGetDTO> crearSala(SalaPostDTO sala) async {
    try {
      final response = await _apiService.post(
        '/api/salas',
        sala.toJson(),
        requiresAuth: true,
      );

      return SalaGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la sala: ${e.toString()}');
    }
  }

  // Actualizar una sala
  Future<SalaGetDTO> actualizarSala(int id, SalaPutDTO sala) async {
    final response = await _apiService.put('/api/salas/$id', sala.toJson());
    return SalaGetDTO.fromJson(response);
  }

  //Borrar sala
  Future<void> borrarSala(int id) async {
    await _apiService.delete('/api/salas/$id');
  }

}
