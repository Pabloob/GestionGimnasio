import 'dart:io';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/Login.dart';
import 'package:frontend/models/get/UsuarioGetDTO.dart';
import 'package:frontend/models/put/UsuarioPutDTO.dart';

class ApiUsuario {
  final ApiService _apiService;

  ApiUsuario({required ApiService apiService}) : _apiService = apiService;
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  Future<Map<String, dynamic>> login(UsuarioLoginDTO usuario) async {
    try {
      final response = await _apiService.post(
        '/api/usuarios/login',
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
      throw HttpException('Error en la autenticación: ${e.toString()}');
    }
  }

  // Método para logout
  Future<void> logout() async {
    await _storage.delete(key: 'jwt_token');
  }

  Future<List<UsuarioGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/usuarios');
    return (response as List)
        .map((json) => UsuarioGetDTO.fromJson(json))
        .toList();
  }

  // Obtener un usuario por ID
  Future<UsuarioGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/usuarios/$id');
    return UsuarioGetDTO.fromJson(response);
  }

  // Obtener un usuario por correo
  Future<UsuarioGetDTO> obtenerPorCorreo(String correo) async {
    final response = await _apiService.get('/api/usuarios/correo/$correo');
    return UsuarioGetDTO.fromJson(response);
  }

  // Verificar si existe un usuario con el correo proporcionado
  Future<bool> existe(String correo) async {
    final response = await _apiService.get('/api/usuarios/existe/$correo');
    return response;
  }

  // Actualizar un usuario
  Future<UsuarioGetDTO> actualizarUsuario(int id, UsuarioPutDTO usuario) async {
    final response = await _apiService.put('/api/usuarios/$id', usuario.toJson());
    return UsuarioGetDTO.fromJson(response);
  }

  // Desactivar un usuario
  Future<void> desactivarUsuario(int id) async {
    await _apiService.put('/api/usuarios/desactivar/$id', {});
  }
}