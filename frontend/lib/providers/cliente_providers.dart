import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/get/CustomerGetDTO.dart';
import '../models/get/EnrollmentGetDTO.dart';
import '../models/get/FitnessClassGetDTO.dart';
import '../models/post/CustomerPostDTO.dart';
import '../models/put/CustomerPutDTO.dart';
import 'common_providers.dart';


final clienteSelectedClasesProvider =
StateProvider.autoDispose<List<FitnessClassGetDTO>>((ref) => []);

final clienteAvaibleClasesProvider =
FutureProvider.autoDispose<List<FitnessClassGetDTO>>((ref) async {
  final user = await ref.watch(userProvider.future);
  final claseService = ref.read(claseServiceProvider);
  final inscripcionService = ref.read(inscripcionServiceProvider);

  final allClasses = await claseService.getAllClasses();
  final inscripciones = await inscripcionService.getEnrollmentsByCustomer(
    user.user.id,
  );

  return allClasses
      .where((clase) => !inscripciones.any((c) => c.fitnessClass.id == clase.id))
      .toList();
});

final clienteClasesProvider =
FutureProvider.autoDispose<List<EnrollmentGetDTO>>((ref) async {
  final userId = await ref.watch(
    userIdProvider.future,
  );

  final api = ref.watch(inscripcionServiceProvider);
  return api.getEnrollmentsByCustomer(userId);
});

final registerClienteProvider = FutureProvider.autoDispose
    .family<CustomerGetDTO, CustomerPostDTO>((ref, request) async {
  final clienteService = ref.watch(clienteServiceProvider);
  return await clienteService.createCustomer(request);
});

class UpdateClienteParams {
  final int id;
  final CustomerPutDTO request;

  UpdateClienteParams(this.id, this.request);
}

final updateClienteProvider = FutureProvider.autoDispose
    .family<CustomerGetDTO, UpdateClienteParams>((ref, params) async {
  final clienteService = ref.watch(clienteServiceProvider);
  return await clienteService.updateCustomer(params.id, params.request);
});