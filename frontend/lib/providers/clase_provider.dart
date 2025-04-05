import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/apis/api_clase.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/post/ClasePostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final AllClassesProvider = FutureProvider.autoDispose<List<ClaseGetDTO>>((
  ref,
) async {
  final api = ApiClase(apiService: ApiService());
  return api.obtenerTodos();
});

final registerClaseProvider = FutureProvider.autoDispose
    .family<ClaseGetDTO, ClasePostDTO>((ref, request) async {
      final api = ref.watch(claseServiceProvider);
      return await api.crearClase(request);
    });

final cuposDisponibles = FutureProvider.autoDispose.family<int, int>((
  ref,
  id,
) async {
  final api = ref.watch(claseServiceProvider);
  return await api.obtenerCuposDisponibles(id);
});
