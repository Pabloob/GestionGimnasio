import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/RoomGetDTO.dart';
import 'package:frontend/providers/common_providers.dart';


final AllSalasProvider = FutureProvider.autoDispose<List<RoomGetDTO>>((
  ref,
) async {
  final api = ref.read(salaServiceProvider);
  return api.getAllRooms();
});

