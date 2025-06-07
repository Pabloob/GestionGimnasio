import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:GymHub/models/get/FitnessClassGetDTO.dart';
import 'package:GymHub/providers/common_providers.dart';

final AllClassesProvider = FutureProvider.autoDispose<List<FitnessClassGetDTO>>(
  (ref) async {
    final api = ref.watch(claseServiceProvider);
    return api.getAllClasses();
  },
);
