import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:GymHub/models/get/ScheduleGetDTO.dart';
import 'package:GymHub/models/post/SchedulePostDTO.dart';
import 'package:GymHub/providers/common_providers.dart';

final AllHorariosProvider = FutureProvider.autoDispose<List<ScheduleGetDTO>>((
  ref,
) async {
  final api = ref.watch(horarioServiceProvider);
  return api.getAllSchedules();
});

final registerHorarioProvider = FutureProvider.autoDispose
    .family<ScheduleGetDTO, SchedulePostDTO>((ref, request) async {
      final api = ref.watch(horarioServiceProvider);
      return await api.createSchedule(request);
    });

final getByClase = FutureProvider.autoDispose.family<List<ScheduleGetDTO>, int>(
  (ref, idClase) async {
    final api = ref.watch(horarioServiceProvider);
    return await api.getSchedulesByClass(idClase);
  },
);

final getByInstructor = FutureProvider.autoDispose
    .family<List<ScheduleGetDTO>, int>((ref, idInstructor) async {
      final api = ref.watch(horarioServiceProvider);
      return await api.getSchedulesByInstructor(idInstructor);
    });

