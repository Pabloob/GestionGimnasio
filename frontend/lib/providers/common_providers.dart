import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_cliente.dart';
import 'package:frontend/apis/api_horario.dart';
import 'package:frontend/apis/api_inscripcion.dart';
import 'package:frontend/apis/api_pago.dart';
import 'package:frontend/apis/api_sala.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_trabajador.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/utils/utils.dart';

final userProvider = FutureProvider.autoDispose<dynamic>((ref) async {
  final authService = AuthService();
  try {
    final user = await authService.getSavedUser();
    return user;
  } catch (e) {
    throw Exception('Error al cargar usuario: ${e.toString()}');
  }
});

// Proveedor del servicio API genérico
final apiServiceProvider = Provider<ApiService>((ref) {
  return ApiService();
});

// Proveedor para ApiClase
final claseServiceProvider = Provider<ApiClase>((ref) {
  return ApiClase(apiService: ref.watch(apiServiceProvider));
});

// Proveedor para ApiInscripcion
final inscripcionServiceProvider = Provider<ApiInscripcion>((ref) {
  return ApiInscripcion(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiUsuario
final usuarioServiceProvider = Provider<ApiUsuario>((ref) {
  return ApiUsuario(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiCliente
final clienteServiceProvider = Provider<ApiCliente>((ref) {
  return ApiCliente(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiTrabajador
final trabajadorServiceProvider = Provider<ApiTrabajador>((ref) {
  return ApiTrabajador(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiHorario
final horarioServiceProvider = Provider<ApiHorario>((ref) {
  return ApiHorario(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiSala
final salaServiceProvider = Provider<ApiSala>((ref) {
  return ApiSala(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiPago
final pagoServiceProvider = Provider<ApiPago>((ref) {
  return ApiPago(apiService: ref.watch(apiServiceProvider));
});
