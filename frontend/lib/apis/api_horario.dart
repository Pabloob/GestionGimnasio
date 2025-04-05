import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/models/get/HorarioGetDTO.dart';
import 'package:frontend/models/post/HorarioPostDTO.dart';
import 'package:frontend/models/put/HorarioPutDTO.dart';

class ApiHorario {
  final ApiService _apiService;

  ApiHorario({required ApiService apiService}) : _apiService = apiService;

  // Obtener todas los horarios
  Future<List<HorarioGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/horarios');
    return (response as List)
        .map((json) => HorarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener un horario por ID
  Future<HorarioGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/horarios/$id');
    return HorarioGetDTO.fromJson(response);
  }

  // Obtener horario por clase
  Future<List<HorarioGetDTO>> obtenerPorClase(int id) async {
    final response = await _apiService.get('/api/horarios/clase/$id');
    return (response as List)
        .map((json) => HorarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener horario por sala
  Future<List<HorarioGetDTO>> obtenerPorSala(int id) async {
    final response = await _apiService.get('/api/horarios/sala/$id');
    return (response as List)
        .map((json) => HorarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener horario por instructor
  Future<List<HorarioGetDTO>> obtenerPorInstructor(int id) async {
    final response = await _apiService.get('/api/horarios/instructor/$id');
    return (response as List)
        .map((json) => HorarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener inscripciones por dia
  Future<List<HorarioGetDTO>> obtenerPorDia(DiaSemana dia) async {
    final response = await _apiService.get('/api/horarios/dia/$dia');
    return (response as List)
        .map((json) => HorarioGetDTO.fromJson(json))
        .toList();
  }

  // Crear un nuevo horario
  Future<HorarioGetDTO> crearHorario(
    HorarioPostDTO inscripcion,
  ) async {
    try {
      final response = await _apiService.post(
        '/api/horarios',
        inscripcion.toJson(),
        requiresAuth: true,
      );

      return HorarioGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear la inscripcion: ${e.toString()}');
    }
  }

  // Actualizar un horario
  Future<HorarioGetDTO> actualizarHorario(
    int id,
    HorarioPutDTO inscripcion,
  ) async {
    final response = await _apiService.put(
      '/api/horarios/$id',
      inscripcion.toJson(),
    );
    return HorarioGetDTO.fromJson(response);
  }

  // Eliminar un horario
  Future<void> eliminarHorario(int id) async {
    await _apiService.delete('/api/horarios/$id');
  }
}
