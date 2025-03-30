import 'dart:io';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Login.dart';
import 'package:frontend/models/get/UsuarioGetDTO.dart';
import 'package:frontend/models/put/UsuarioPutDTO.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiUsuario {
  final ApiService _apiService;

  ApiUsuario({required ApiService apiService}) : _apiService = apiService;
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  Future<Map<String, dynamic>> login(UsuarioLoginDTO usuario) async {
    try {
      final response = await _apiService.post(
        '/api/auth/login',
        usuario.toJson(),
        requiresAuth: false,
      );
      if (response.containsKey('token')) {
        await _storage.write(key: 'jwt_token', value: response['token']);
        return response;
      } else {
        throw HttpException('Error en la autenticación: Token no encontrado.');
      }
    } catch (e) {
      throw HttpException('No se pudo crear el cliente: ${e.toString()}');
    }
  }

  // Método para logout
  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.clear();
    await _storage.delete(key: 'jwt_token');
  }

  Future<List<UsuarioGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/auth');
    return (response as List)
        .map((json) => UsuarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener un cliente por ID
  Future<UsuarioGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/auth/$id');
    return UsuarioGetDTO.fromJson(response);
  }

  // Obtener un cliente por correo
  Future<UsuarioGetDTO> obtenerPorCorreo(String correo) async {
    final response = await _apiService.get('/api/auth/correo/$correo');
    return UsuarioGetDTO.fromJson(response);
  }

  // Obtener un cliente por ID
  Future<bool> existe(String correo) async {
    final response = await _apiService.get('/api/auth/existe/$correo');

    return response;
  }

  // Actualizar un cliente
  Future<UsuarioGetDTO> actualizarCliente(int id, UsuarioPutDTO cliente) async {
    final response = await _apiService.put('/api/auth/$id', cliente.toJson());
    return UsuarioGetDTO.fromJson(response);
  }

  // Eliminar un cliente
  Future<void> desactivarUsuario(int id) async {
    await _apiService.put('/api/desactivar/$id', {});
  }
}
