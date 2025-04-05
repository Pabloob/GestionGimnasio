import 'package:flutter/material.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/models/get/UsuarioGetDTO.dart';

class TrabajadorGetDTO {
  final UsuarioGetDTO usuario;
  final String direccion;
  final TimeOfDay horaInicio;
  final TimeOfDay horaFin;
  final TipoTrabajador tipoTrabajador;

  TrabajadorGetDTO({
    required this.usuario,
    required this.direccion,
    required this.horaInicio,
    required this.horaFin,
    required this.tipoTrabajador,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorGetDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorGetDTO(
      usuario: UsuarioGetDTO.fromJson(json['usuario'] as Map<String, dynamic>),
      direccion: json['direccion'],
      horaInicio: TimeOfDay.fromDateTime(
        DateTime.parse("1970-01-01T${json['horaInicio']}"),
      ),
      horaFin: TimeOfDay.fromDateTime(
        DateTime.parse("1970-01-01T${json['horaFin']}"),
      ),
      tipoTrabajador: TipoTrabajador.values.firstWhere(
        (e) => e.name == json["tipoTrabajador"],
      ),
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'usuario': usuario.toJson(),
      'direccion': direccion,
      'horaInicio':
          "${horaInicio.hour.toString().padLeft(2, '0')}:${horaInicio.minute.toString().padLeft(2, '0')}",
      'horaFin':
          "${horaFin.hour.toString().padLeft(2, '0')}:${horaFin.minute.toString().padLeft(2, '0')}",
      'tipoTrabajador': tipoTrabajador.name,
    };
  }

  String getHorario() {
    return "Horario: ${horaInicio.hour}:${horaInicio.minute} - ${horaFin.hour}:${horaFin.minute}";
  }
}
