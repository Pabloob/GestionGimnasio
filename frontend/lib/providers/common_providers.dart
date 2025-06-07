import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:GymHub/apis/api_clase.dart';
import 'package:GymHub/apis/api_cliente.dart';
import 'package:GymHub/apis/api_horario.dart';
import 'package:GymHub/apis/api_inscripcion.dart';
import 'package:GymHub/apis/api_pago.dart';
import 'package:GymHub/apis/api_sala.dart';
import 'package:GymHub/apis/api_service.dart';
import 'package:GymHub/apis/api_trabajador.dart';
import 'package:GymHub/apis/api_usuario.dart';

import '../utils/authService.dart';

final userProvider = FutureProvider.autoDispose<dynamic>((ref) async {
  final authService = AuthService();
  try {
    final user = await authService.getSavedUser();
    return user;
  } catch (e) {
    throw Exception('Error al cargar usuario: ${e.toString()}');
  }
});


final userIdProvider = FutureProvider.autoDispose<dynamic>((ref) async {
  final authService = AuthService();
  try {
    final user = await authService.getUserId();
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
final claseServiceProvider = Provider<FitnessClassController>((ref) {
  return FitnessClassController(apiService: ref.watch(apiServiceProvider));
});

// Proveedor para ApiInscripcion
final inscripcionServiceProvider = Provider<EnrollmentController>((ref) {
  return EnrollmentController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiUsuario
final usuarioServiceProvider = Provider<UserController>((ref) {
  return UserController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiCliente
final clienteServiceProvider = Provider<CustomerController>((ref) {
  return CustomerController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiTrabajador
final trabajadorServiceProvider = Provider<StaffMemberController>((ref) {
  return StaffMemberController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiHorario
final horarioServiceProvider = Provider<ScheduleController>((ref) {
  return ScheduleController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiSala
final salaServiceProvider = Provider<RoomController>((ref) {
  return RoomController(apiService: ref.watch(apiServiceProvider));
});

//Proveedor para ApiPago
final pagoServiceProvider = Provider<PaymentController>((ref) {
  return PaymentController(apiService: ref.watch(apiServiceProvider));
});