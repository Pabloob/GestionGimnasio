import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/apis/api_horario.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/get/HorarioGetDTO.dart';
import 'package:frontend/models/post/HorarioPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final AllHorariosProvider = FutureProvider.autoDispose<List<HorarioGetDTO>>((
  ref,
) async {
  final api = ApiHorario(apiService: ApiService());
  return api.obtenerTodos();
});

final registerHorarioProvider = FutureProvider.autoDispose
    .family<HorarioGetDTO, HorarioPostDTO>((ref, request) async {
      final api = ref.watch(horarioServiceProvider);
      return await api.crearHorario(request);
    });

final getByClase = FutureProvider.autoDispose.family<List<HorarioGetDTO>, int>((
  ref,
  idClase,
) async {
  final api = ref.watch(horarioServiceProvider);
  return await api.obtenerPorClase(idClase);
});

final getByInstructor = FutureProvider.autoDispose
    .family<List<HorarioGetDTO>, int>((ref, idInstructor) async {
      final api = ref.watch(horarioServiceProvider);
      return await api.obtenerPorInstructor(idInstructor);
    });

