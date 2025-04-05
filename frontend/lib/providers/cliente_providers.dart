import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';
import 'package:frontend/models/get/InscripcionGetDTO.dart';
import 'package:frontend/models/post/ClientePostDTO.dart';
import 'package:frontend/models/put/ClientePutDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final clienteSelectedClasesProvider =
    StateProvider.autoDispose<List<ClaseGetDTO>>((ref) => []);

final clienteAvaibleClasesProvider =
    FutureProvider.autoDispose<List<ClaseGetDTO>>((ref) async {
      final user = await ref.watch(userProvider.future);
      final claseService = ref.read(claseServiceProvider);
      final inscripcionService = ref.read(inscripcionServiceProvider);

      final allClasses = await claseService.obtenerTodos();
      final inscripciones = await inscripcionService.obtenerPorCliente(
        user.usuario.id,
      );

      return allClasses
          .where((clase) => !inscripciones.any((c) => c.clase.id == clase.id))
          .toList();
    });

final clienteClasesProvider =
    FutureProvider.autoDispose<List<InscripcionGetDTO>>((ref) async {
      final userId = await ref.watch(
        userProvider.selectAsync((user) => user.usuario.id),
      );
      final api = ref.watch(inscripcionServiceProvider);
      return api.obtenerPorCliente(userId);
    });

final registerClienteProvider = FutureProvider.autoDispose
    .family<ClienteGetDTO, ClientePostDTO>((ref, request) async {
      final clienteService = ref.watch(clienteServiceProvider);
      return await clienteService.crearCliente(request);
    });

class UpdateClienteParams {
  final int id;
  final ClientePutDTO request;

  UpdateClienteParams(this.id, this.request);
}

final updateClienteProvider = FutureProvider.autoDispose
    .family<ClienteGetDTO, UpdateClienteParams>((ref, params) async {
      final clienteService = ref.watch(clienteServiceProvider);
      return await clienteService.actualizarCliente(params.id, params.request);
    });
