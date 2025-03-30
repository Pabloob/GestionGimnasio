import 'dart:ffi';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/models/post/TrabajadorPostDTO.dart';
import 'package:frontend/models/put/TrabajadorPutDTO.dart';

class ApiTrabajador {
  final ApiService _apiService;

  ApiTrabajador({required ApiService apiService}) : _apiService = apiService;

  // Obtener todos los trabajadores
  Future<List<TrabajadorGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/trabajadores');
    return (response as List)
        .map((json) => TrabajadorGetDTO.fromJson(json))
        .toList();
  }

  // Obtener un trabajador por ID
  Future<TrabajadorGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/trabajadores/$id');
    return TrabajadorGetDTO.fromJson(response);
  }

  // Obtener un trabajador por tipo
  Future<List<TrabajadorGetDTO>> obtenerPorTipo(TipoTrabajador tipo) async {
    final response = await _apiService.get('/api/trabajadores/tipos/$tipo');
    return (response as List)
        .map((json) => TrabajadorGetDTO.fromJson(json))
        .toList();
  }

  // Obtener clases de un trabajador
  Future<List<ClaseGetDTO>> obtenerClases(Long id) async {
    final response = await _apiService.get('/api/trabajadores/clases/$id');
    return (response as List)
        .map((json) => ClaseGetDTO.fromJson(json))
        .toList();
  }

  // Crear un nuevo trabajador
  Future<TrabajadorGetDTO> crearTrabajador(TrabajadorPostDTO trabajador) async {
    final response = await _apiService.post(
      '/api/trabajadores',
      trabajador.toJson(),
      requiresAuth: true,
    );
    return TrabajadorGetDTO.fromJson(response);
  }

  // Actualizar un trabajador
  Future<TrabajadorGetDTO> actualizarTrabajador(
    Long id,
    TrabajadorPutDTO trabajador,
  ) async {
    final response = await _apiService.put(
      '/api/trabajadores/$id',
      trabajador.toJson(),
    );
    return TrabajadorGetDTO.fromJson(response);
  }

}
