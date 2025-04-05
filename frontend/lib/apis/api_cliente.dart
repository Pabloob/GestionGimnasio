import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';
import 'package:frontend/models/post/ClientePostDTO.dart';
import 'package:frontend/models/put/ClientePutDTO.dart';

class ApiCliente {
  final ApiService _apiService;

  ApiCliente({required ApiService apiService}) : _apiService = apiService;

  // Obtener todos los clientes
  Future<List<ClienteGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/clientes');
    return (response as List)
        .map((json) => ClienteGetDTO.fromJson(json))
        .toList();
  }

  // Obtener un cliente por ID
  Future<ClienteGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/clientes/$id');
    return ClienteGetDTO.fromJson(response);
  }

  // Crear un nuevo cliente
  Future<ClienteGetDTO> crearCliente(ClientePostDTO clientePostDTO) async {
    try {

      final response = await _apiService.post(
        '/api/clientes',
        clientePostDTO.toJson(),
        requiresAuth: false,
      );

      return ClienteGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear el cliente: ${e.toString()}');
    }
  }

  // Actualizar un cliente
  Future<ClienteGetDTO> actualizarCliente(int id, ClientePutDTO cliente) async {
    final response = await _apiService.put(
      '/api/clientes/$id',
      cliente.toJson(),
    );
    return ClienteGetDTO.fromJson(response);
  }

  // Incrementar inasistencias de un cliente
  Future<void> borrarCliente(int id) async {
    await _apiService.delete('/api/clientes/$id');
  }
}
