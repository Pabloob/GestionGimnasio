import 'package:frontend/models/enums.dart';
import 'package:frontend/models/put/UsuarioPutDTO.dart';
import 'package:intl/intl.dart';

class TrabajadorPutDTO {
  final UsuarioPutDTO? usuario;
  final String? direccion;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final TipoTrabajador? tipoTrabajador;

  TrabajadorPutDTO({
    this.usuario,
    this.direccion,
    this.horaInicio,
    this.horaFin,
    this.tipoTrabajador,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorPutDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorPutDTO(
      usuario: UsuarioPutDTO.fromJson(json['usuario']),
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
      'usuario': usuario?.toJson(),
      'direccion': direccion,
      'horaInicio': horaInicio?.toIso8601String(),
      'horaFin': horaFin?.toIso8601String(),
      'tipoTrabajador': tipoTrabajador?.name,
    };
  }
}
