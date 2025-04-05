import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/PagoGetDTO.dart';
import 'package:frontend/models/post/PagoPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final registerPagoProvider = FutureProvider.autoDispose
    .family<PagoGetDTO, PagoPostDTO>((ref, request) async {
      final api = ref.watch(pagoServiceProvider);
      return await api.crearPago(request);
    });
