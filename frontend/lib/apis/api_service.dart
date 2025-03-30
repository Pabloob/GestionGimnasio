import 'dart:convert';
import 'dart:io';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;

class ApiService {
  final String _baseUrl;
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  ApiService({String baseUrl = 'http://192.168.0.14:8080'})
      : _baseUrl = baseUrl;

  // Método genérico para manejar solicitudes HTTP
  Future<dynamic> _request(
      String method,
      String endpoint, {
        Map<String, dynamic>? body,
        bool requiresAuth = true,
      }) async {
    try {
      final uri = Uri.parse('$_baseUrl$endpoint');
      late http.Response response;
      final headers = <String, String>{'Content-Type': 'application/json'};

      // Agregar token JWT si es necesario
      if (requiresAuth) {
        final token = await _storage.read(key: 'jwt_token');
        if (token == null) {
          throw Exception('No se encontró un token JWT válido.');
        }
        headers['Authorization'] = 'Bearer $token';
      }

      switch (method.toUpperCase()) {
        case 'GET':
          response = await http.get(uri, headers: headers);
          break;
        case 'POST':
          response = await http.post(
            uri,
            headers: headers,
            body: json.encode(body),
          );
          break;
        case 'PUT':
          response = await http.put(
            uri,
            headers: headers,
            body: json.encode(body),
          );
          break;
        case 'DELETE':
          response = await http.delete(uri, headers: headers);
          break;
        default:
          throw Exception('Método HTTP no soportado: $method');
      }

      return _handleResponse(response);
    } on SocketException {
      throw Exception(
        'No se pudo conectar al servidor. Verifique su conexión de red.',
      );
    } on http.ClientException {
      throw Exception('Error de conexión con el servidor.');
    } catch (e) {
      throw Exception('Error inesperado: ${e.toString()}');
    }
  }

  dynamic _handleResponse(http.Response response) {
    final statusCode = response.statusCode;

    if (response.body.isEmpty) {
      if (statusCode == 204) {
        return null; // Respuesta sin contenido
      }
      throw Exception('Respuesta vacía con estado $statusCode');
    }

    try {
      final decoded = json.decode(response.body);

      if (statusCode >= 200 && statusCode < 300) {
        return decoded;
      } else {
        final errorMsg =
            decoded['message'] ?? decoded['error'] ?? 'HTTP $statusCode';
        throw Exception(errorMsg);
      }
    } on FormatException {
      throw Exception('Respuesta JSON inválida: ${response.body}');
    }
  }

  // Métodos públicos para cada tipo de solicitud
  Future<dynamic> get(String endpoint, {bool requiresAuth = true}) async {
    return await _request('GET', endpoint, requiresAuth: requiresAuth);
  }

  Future<dynamic> post(
      String endpoint,
      Map<String, dynamic> body, {
        bool requiresAuth = true,
      }) async {
    return await _request(
      'POST',
      endpoint,
      body: body,
      requiresAuth: requiresAuth,
    );
  }

  Future<dynamic> put(
      String endpoint,
      Map<String, dynamic> body, {
        bool requiresAuth = true,
      }) async {
    return await _request(
      'PUT',
      endpoint,
      body: body,
      requiresAuth: requiresAuth,
    );
  }

  Future<void> delete(String endpoint, {bool requiresAuth = true}) async {
    await _request('DELETE', endpoint, requiresAuth: requiresAuth);
  }

}