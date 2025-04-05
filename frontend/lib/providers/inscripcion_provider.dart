import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/InscripcionGetDTO.dart';
import 'package:frontend/models/post/InscripcionPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final registerInscripcionProvider = FutureProvider.autoDispose
    .family<InscripcionGetDTO, InscripcionPostDTO>((ref, request) async {
      final clienteService = ref.watch(inscripcionServiceProvider);
      return await clienteService.crearInscripcion(request);
    });
