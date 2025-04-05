import 'dart:io';

import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/PagoGetDTO.dart';
import 'package:frontend/models/post/PagoPostDTO.dart';
import 'package:frontend/models/put/PagoPutDTO.dart';

class ApiPago {
  final ApiService _apiService;

  ApiPago({required ApiService apiService}) : _apiService = apiService;

  // Obtener todos los pagos
  Future<List<PagoGetDTO>> obtenerTodos() async {
    final response = await _apiService.get('/api/pagos');
    return (response as List).map((json) => PagoGetDTO.fromJson(json)).toList();
  }

  // Obtener pago por ID
  Future<PagoGetDTO> obtenerPorId(int id) async {
    final response = await _apiService.get('/api/pagos/$id');
    return PagoGetDTO.fromJson(response);
  }

  // Obtener pago por ID de cliente
  Future<PagoGetDTO> obtenerPoCliented(int id) async {
    final response = await _apiService.get('/api/pagos/cliente/$id');
    return PagoGetDTO.fromJson(response);
  }

  // Crear un nuevo pago
  Future<PagoGetDTO> crearPago(PagoPostDTO pago) async {
    try {
      final response = await _apiService.post(
        '/api/pagos',
        pago.toJson(),
        requiresAuth: true,
      );

      return PagoGetDTO.fromJson(response);
    } catch (e) {
      throw HttpException('No se pudo crear el pago: ${e.toString()}');
    }
  }

  // Actualizar un pago
  Future<PagoGetDTO> actualizarPago(int id, PagoPutDTO pago) async {
    final response = await _apiService.put('/api/pagos/$id', pago.toJson());
    return PagoGetDTO.fromJson(response);
  }

  // Eliminar un pago
  Future<void> eliminarPago(int id) async {
    await _apiService.delete('/api/pagos/$id');
  }

  // Marcar pago como completado
  Future<void> procesarPago(int id) async {
    await _apiService.patch('/api/pagos/$id/procesar');
  }

  // Marcar pago como completado
  Future<void> cancelarPago(int id) async {
    await _apiService.patch('/api/pagos/$id/cancelar');
  }
}
