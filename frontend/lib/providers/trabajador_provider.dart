import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_trabajador.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/models/post/TrabajadorPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';

final AllTrabajadoresProvider =
    FutureProvider.autoDispose<List<TrabajadorGetDTO>>((ref) async {
      final api = ApiTrabajador(apiService: ApiService());
      return api.obtenerTodos();
    });

final registerTrabajadorProvider = FutureProvider.autoDispose
    .family<TrabajadorGetDTO, TrabajadorPostDTO>((ref, request) async {
      final api = ref.watch(trabajadorServiceProvider);
      return await api.crearTrabajador(request);
    });
