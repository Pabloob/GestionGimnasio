import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Cliente.dart';
import 'package:frontend/models/Login.dart';

class ApiCliente {
  final ApiService _apiService;

  ApiCliente({required ApiService apiService}) : _apiService = apiService;

  // Obtener todos los clientes
  Future<List<Cliente>> obtenerTodos() async {
    final response = await _apiService.get('/api/clientes');
    return (response as List).map((json) => Cliente.fromJson(json)).toList();
  }

  // Obtener un cliente por ID
  Future<Cliente> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/clientes/$id');
    return Cliente.fromJson(response);
  }

  // Crear un nuevo cliente
  Future<Cliente> crearCliente(Cliente cliente) async {
    try {
      final response = await _apiService.post(
        '/api/clientes',
        cliente.toJson(),
        requiresAuth: false,
      );

      return Cliente.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear el cliente: ${e.toString()}');
    }
  }

  // Actualizar un cliente
  Future<Cliente> actualizarCliente(int id, Cliente cliente) async {
    final response = await _apiService.put(
      '/api/clientes/$id',
      cliente.toJson(),
    );
    return Cliente.fromJson(response);
  }

  // Eliminar un cliente
  Future<void> eliminarCliente(int id) async {
    await _apiService.delete('/api/clientes/$id');
  }

  // Incrementar inasistencias de un cliente
  Future<Cliente> incrementarInasistencias(int id) async {
    final response = await _apiService.put(
      '/api/clientes/$id/inasistencias',
      {},
    );
    return Cliente.fromJson(response);
  }

  // Autenticar y obtener ID
  Future<int> autenticarCliente(Login login) async {
    final response = await _apiService.post(
      '/api/clientes/authenticate',
      login.toJson(),
    );
    return response as int;
  }
  
}
