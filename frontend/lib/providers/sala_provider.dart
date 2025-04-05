import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/SalaGetDTO.dart';
import 'package:frontend/models/post/SalaPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final AllSalasProvider = FutureProvider.autoDispose<List<SalaGetDTO>>((
  ref,
) async {
  final api = ref.read(salaServiceProvider);
  return api.obtenerTodos();
});

final registerSalaProvider = FutureProvider.autoDispose
    .family<SalaGetDTO, SalaPostDTO>((ref, request) async {
      final api = ref.watch(salaServiceProvider);
      return await api.crearSala(request);
    });
