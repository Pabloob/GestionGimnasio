import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/Login.dart';
import 'package:frontend/providers/common_providers.dart';

final loginProvider = FutureProvider.autoDispose
    .family<Map<String, dynamic>, UsuarioLoginDTO>((ref, request) async {
      final usuarioService = ref.watch(usuarioServiceProvider);
      return await usuarioService.login(request);
    });
