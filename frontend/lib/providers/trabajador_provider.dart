import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/StaffMemberGetDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final AllTrabajadoresProvider =
FutureProvider.autoDispose<List<StaffMemberGetDTO>>((ref) async {
  final api = ref.watch(trabajadorServiceProvider);
  return api.getAllStaffMembers();
});
