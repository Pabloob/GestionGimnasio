import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:GymHub/models/get/RoomGetDTO.dart';
import 'package:GymHub/providers/common_providers.dart';


final AllSalasProvider = FutureProvider.autoDispose<List<RoomGetDTO>>((
  ref,
) async {
  final api = ref.read(salaServiceProvider);
  return api.getAllRooms();
});

