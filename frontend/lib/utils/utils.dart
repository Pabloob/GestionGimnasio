import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/pages/auth_pages/welcome.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';

Future<void> restartApp(BuildContext context) async {
  try {
    final apiUsuario = ApiUsuario(apiService: ApiService());
    await apiUsuario.logout();

    // Verificar si el widget todavía está montado
    if (!context.mounted) return;

    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => const WelcomePage()),
      (Route<dynamic> route) => false,
    );
  } catch (e) {
    // Opcional: Manejar el error si es necesario
    if (context.mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Error al reiniciar la aplicación')),
      );
    }
  }
}

// Función para obtener el ID del usuario desde el token almacenado
Future<int?> obtenerUserIdDesdeToken() async {
  final prefs = await SharedPreferences.getInstance();
  String? token = prefs.getString('token');
  if (token != null && token.isNotEmpty) {
    // Decodificar el token y obtener el "userId" del payload
    Map<String, dynamic> decodedToken = JwtDecoder.decode(token);
    return decodedToken['userId'];
  }

  return null; // Si no se encuentra el token, retornamos null
}

// Función para obtener el ID del usuario desde el token almacenado
Future<String?> obtenerTipoUsuarioIdDesdeToken() async {
  final prefs = await SharedPreferences.getInstance();
  String? token = prefs.getString('token');

  if (token != null && token.isNotEmpty) {
    // Decodificar el token y obtener el "rol" del payload
    Map<String, dynamic> decodedToken = JwtDecoder.decode(token);
    return decodedToken['roles'];
  }

  return null;
}

// Función para obtener el usuario desde el token almacenado
Future<dynamic> obtenerUsuarioGuardado() async {
  final prefs = await SharedPreferences.getInstance();
  final String? usuarioJson = prefs.getString('usuario');

  if (usuarioJson == null) {
    return null; // No hay usuario guardado
  }

  try {
    // Deserializar el JSON a un mapa
    final Map<String, dynamic> usuarioMap = jsonDecode(usuarioJson);

    // Verificar el tipo de usuario y castearlo
    if (usuarioMap.containsKey('tipoUsuario')) {
      final tipoUsuario = usuarioMap['tipoUsuario'];
      if (tipoUsuario == 'CLIENTE') {
        return ClienteGetDTO.fromJson(usuarioMap);
      } else if (tipoUsuario == 'TRABAJADOR') {
        return TrabajadorGetDTO.fromJson(usuarioMap);
      }
    }
  } catch (e) {
    return null;
  }

  return null;
}

// Función para mostrar mensajes
void mostrarMensaje(String mensaje, BuildContext context) {
  ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(mensaje)));
}

TimeOfDay parseTimeOfDay(String hora) {
  List<String> partes = hora.split(":");
  return TimeOfDay(hour: int.parse(partes[0]), minute: int.parse(partes[1]));
}
