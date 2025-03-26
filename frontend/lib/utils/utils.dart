import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../models/Cliente.dart';
import '../models/Trabajador.dart';

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
    return null;
  }

  try {
    // Deserializar el JSON a un mapa
    final Map<String, dynamic> usuarioMap = jsonDecode(usuarioJson);

    // Verificar el tipo de usuario y castearlo
    if (usuarioMap.containsKey('tipoUsuario')) {
      if (usuarioMap['tipoUsuario'] == 'CLIENTE') {
        return Cliente.fromJson(usuarioMap);
      } else if (usuarioMap['tipoUsuario'] == 'TRABAJADOR') {
        return Trabajador.fromJson(usuarioMap);
      }
    } else {
      return null;
    }
  } catch (e) {
    print('Error al decodificar el usuario: $e');
    return null;
  }
}

// Función para mostrar mensajes
void mostrarMensaje(String mensaje, BuildContext context) {
  ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(mensaje)));
}
