import 'package:frontend/models/enums.dart';
import 'package:frontend/models/post/UsuarioPostDTO.dart';
import 'package:intl/intl.dart';

class TrabajadorPostDTO {
  final UsuarioPostDTO usuario;
  final String direccion;
  final DateTime horaInicio;
  final DateTime horaFin;
  final TipoTrabajador tipoTrabajador;

  TrabajadorPostDTO({
    required this.usuario,
    required this.direccion,
    required this.horaInicio,
    required this.horaFin,
    required this.tipoTrabajador,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorPostDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorPostDTO(
      usuario: UsuarioPostDTO.fromJson(json['usuario']),
      direccion: json['direccion'],
      horaInicio: DateFormat("HH:mm:ss").parse(json['horaInicio']),
      horaFin: DateFormat("HH:mm:ss").parse(json['horaFin']),
      tipoTrabajador: TipoTrabajador.values.firstWhere(
        (e) => e.name == json["tipoTrabajador"],
      ),
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'usuarioPostDTO': usuario.toJson(),
      'direccion': direccion,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'tipoTrabajador': tipoTrabajador.name,
    };
  }
}
