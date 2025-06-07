import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:GymHub/models/get/StaffMemberGetDTO.dart';
import 'package:GymHub/providers/common_providers.dart';

final AllTrabajadoresProvider =
FutureProvider.autoDispose<List<StaffMemberGetDTO>>((ref) async {
  final api = ref.watch(trabajadorServiceProvider);
  return api.getStaffMembersByType("INSTRUCTOR");
});
