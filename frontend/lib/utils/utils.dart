import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/pages/auth_pages/welcome.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../pages/admin_pages/admin_main.dart';
import '../pages/client_pages/client_main.dart';
import '../pages/instructor_pages/instructor_main.dart';

class AuthService {
  static const String _tokenKey = 'auth_token';
  static const String _userKey = 'auth_user';
  static const String _loginTimeKey = 'auth_login_time';
  static const Duration _sessionDuration = Duration(hours: 8);

  // Guardar datos de sesión
  Future<void> saveSession(String token, dynamic user) async {
    final prefs = await SharedPreferences.getInstance();
    await Future.wait([
      prefs.setString(_tokenKey, token),
      prefs.setString(_userKey, jsonEncode(user.toJson())),
      prefs.setString(_loginTimeKey, DateTime.now().toIso8601String()),
    ]);
  }

  // Cerrar sesión
  Future<void> logout(BuildContext context) async {
    ApiUsuario apiUsuario = ApiUsuario(apiService: ApiService());
    try {
      await apiUsuario.logout();
      await _clearSessionData();

      if (!context.mounted) return;

      if (!context.mounted) return;

      Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(builder: (context) => const WelcomePage()),
        (Route<dynamic> route) => false,
      );
    } catch (e) {
      if (context.mounted) {
        _showErrorSnackbar(context, 'Error al cerrar sesión');
      }
    }
  }

  // Limpiar datos de sesión
  Future<void> _clearSessionData() async {
    final prefs = await SharedPreferences.getInstance();
    await Future.wait([
      prefs.remove(_tokenKey),
      prefs.remove(_userKey),
      prefs.remove(_loginTimeKey),
    ]);
  }

  // Verificar sesión válida
  Future<bool> isSessionValid() async {
    final prefs = await SharedPreferences.getInstance();
    final loginTimeStr = prefs.getString(_loginTimeKey);

    if (loginTimeStr == null) return false;

    try {
      final loginTime = DateTime.parse(loginTimeStr);
      return DateTime.now().difference(loginTime) < _sessionDuration;
    } catch (e) {
      return false;
    }
  }

  // Obtener token
  Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_tokenKey);
  }

  // Obtener tipo de usuario desde token
  Future<String?> getUserRole() async {
    final token = await getToken();
    if (token == null || token.isEmpty) return null;

    try {
      final decoded = JwtDecoder.decode(token);
      return decoded['roles']?.toString().replaceFirst('ROLE_', '');
    } catch (e) {
      return null;
    }
  }

  // Obtener usuario guardado
  Future<dynamic> getSavedUser() async {
    final prefs = await SharedPreferences.getInstance();
    final userJson = prefs.getString(_userKey);

    if (userJson == null) return null;

    try {
      final Map<String, dynamic> userMap = jsonDecode(userJson);

      if (userMap.containsKey('usuario')) {
        if (userMap['usuario']['tipoUsuario'] == "CLIENTE") {
          return ClienteGetDTO.fromJson(userMap);
        } else {
          return TrabajadorGetDTO.fromJson(userMap);
        }
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  // Obtener ID de usuario
  Future<int?> getUserId() async {
    final user = await getSavedUser();
    return user.usuario.id;
  }

  // Mostrar mensajes de error
  void _showErrorSnackbar(BuildContext context, String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message), backgroundColor: Colors.red),
    );
  }

  // Extrar el usuario de la respuesta de la api con el rol
  dynamic extraerUsuario(Map<String, dynamic> respuesta, dynamic role) {
    if (respuesta.containsKey('userDetails')) {
      final userDetails = respuesta['userDetails'];

      if (role == "CLIENTE") {
        return ClienteGetDTO.fromJson(userDetails);
      } else {
        return TrabajadorGetDTO.fromJson(userDetails);
      }
    }
    return null;
  }

  // Navegar segun el rol del usuario
  Future<void> navegarSegunRol(String role, BuildContext context) async {
    final paginasDestino = {
      "CLIENTE": const ClientMainScreen(),
      "INSTRUCTOR": const InstructorMainScreen(),
      "RECEPCIONISTA": const InstructorMainScreen(),
      "ADMIN": const AdminMainScreen(),
    };

    final paginaDestino = paginasDestino[role];

    if (paginaDestino == null) {
      throw Exception("Rol de usuario no reconocido");
    }

    await Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => paginaDestino),
      (Route<dynamic> route) => false,
    );
  }
}
