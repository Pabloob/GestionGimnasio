import 'dart:ffi';

import 'package:frontend/models/Login.dart' show Login;
import 'package:frontend/models/Trabajador.dart';
import 'package:frontend/apis/api_service.dart';

class ApiTrabajador {
  final ApiService _apiService;

  ApiTrabajador({required ApiService apiService}) : _apiService = apiService;

  // Obtener todos los trabajadores
  Future<List<Trabajador>> obtenerTodos() async {
    final response = await _apiService.get('/api/trabajadores');
    return (response as List).map((json) => Trabajador.fromJson(json)).toList();
  }

  // Obtener un trabajador por ID
  Future<Trabajador> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/trabajadores/$id');
    return Trabajador.fromJson(response);
  }

  // Crear un nuevo trabajador
  Future<Trabajador> crearTrabajador(Trabajador trabajador) async {
    final response = await _apiService.post(
      '/api/trabajadores',
      trabajador.toJson(),
      requiresAuth: false,
    );
    return Trabajador.fromJson(response);
  }

  // Actualizar un trabajador
  Future<Trabajador> actualizarTrabajador(Long id,Trabajador trabajador) async {
    final response = await _apiService.put(
      '/api/trabajadores/$id',
      trabajador.toJson(),
    );
    return Trabajador.fromJson(response);
  }

  // Eliminar un trabajador
  Future<void> eliminarTrabajador(int id) async {
    await _apiService.delete('/api/trabajadores/$id');
  }

  //Obtener trabajadores por tipo
  Future<List<Trabajador>> obteobtenerTrabajadoresPorTiponerTodos(TipoTrabajador tipo) async {
        final response = await _apiService.get(
      '/api/trabajadores/$tipo'
    );
    return (response as List).map((json) => Trabajador.fromJson(json)).toList();
  }

    // Iniciar sesion
    Future<String> iniciarSesion(Login login) async {
    final response = await _apiService.post(
      '/api/trabajadores/login',
      login.toJson(),
    );
    return response as String;
  }

  // Autenticar y obtener ID
  Future<Long> autenticarTrabajador(Login login) async {
    final response = await _apiService.post(
      '/api/trabajadores/authenticate',
      login.toJson(),
    );
    return response as Long;
  }
}