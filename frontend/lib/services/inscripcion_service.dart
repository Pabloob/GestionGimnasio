import 'package:frontend/apis/api_inscripcion.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/post/InscripcionPostDTO.dart';
import 'package:frontend/utils/utils.dart';

class InscripcionService {
  final ApiInscripcion _inscripcionService;

  InscripcionService({required ApiInscripcion inscripcionService})
    : _inscripcionService = inscripcionService;

  // Inscribirse a múltiples clases
  Future<bool> inscribir(List<ClaseGetDTO> classes) async {
    final userId = await obtenerUserIdDesdeToken();
    if (userId == null) return false;

    bool allSuccess = true;

    for (final clase in classes) {
      if (clase.id == null) {
        allSuccess = false;
        continue;
      }
      try {
        await _inscripcionService.crearInscripcion(
          InscripcionPostDTO(clienteId: userId, claseId: clase.id!),
        );
      } catch (e) {
        allSuccess = false;
      }
    }

    return allSuccess;
  }
}
