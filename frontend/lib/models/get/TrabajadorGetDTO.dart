import 'package:flutter/material.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/utils/utils.dart';

class TrabajadorGetDTO {
  final int? id;
  final String? nombre;
  final String? correo;
  final String? telefono;
  final DateTime? fechaNacimiento;
  final String? direccion;
  final TimeOfDay? horaInicio;
  final TimeOfDay? horaFin;
  final TipoTrabajador? tipoTrabajador;
  final bool? activo;
  final List<int>? clasesIds;

  TrabajadorGetDTO({
    this.id,
    this.nombre,
    this.correo,
    this.telefono,
    this.fechaNacimiento,
    this.direccion,
    this.horaInicio,
    this.horaFin,
    this.tipoTrabajador,
    this.activo,
    this.clasesIds,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorGetDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorGetDTO(
      id: json['id'],
      nombre: json['nombre'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaNacimiento: DateTime.parse(json['fechaNacimiento']),
      direccion: json['direccion'],
      horaInicio: parseTimeOfDay(json['horaInicio']),
      horaFin: parseTimeOfDay(json['horaFin']),
      tipoTrabajador: TipoTrabajador.values.firstWhere(
        (e) => e.name == json["tipoTrabajador"],
      ),
      activo: json['activo'],
      clasesIds:
          json['clasesIds'] != null ? List<int>.from(json['clasesIds']) : null,
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento?.toIso8601String(),
      'direccion': direccion,
      'horaInicio': '${horaInicio?.hour}:${horaInicio?.minute}',
      'horaFin': '${horaFin?.hour}:${horaFin?.minute}',
      'tipoTrabajador': tipoTrabajador?.name,
      'activo': activo,
      'clasesIds': clasesIds,
    };
  }
}
