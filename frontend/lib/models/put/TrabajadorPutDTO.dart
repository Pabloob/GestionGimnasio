import 'package:flutter/material.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/utils/utils.dart';

class TrabajadorPutDTO {
  final int id;
  final String? nombre;
  final String? contrasena;
  final String? correo;
  final String? telefono;
  final DateTime? fechaNacimiento;
  final String? direccion;
  final TimeOfDay? horaInicio;
  final TimeOfDay? horaFin;
  final TipoTrabajador? tipoTrabajador;
  final bool? activo;

  TrabajadorPutDTO({
    required this.id,
    this.nombre,
    this.contrasena,
    this.correo,
    this.telefono,
    this.fechaNacimiento,
    this.direccion,
    this.horaInicio,
    this.horaFin,
    this.tipoTrabajador,
    this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorPutDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorPutDTO(
      id: json['id'],
      nombre: json['nombre'],
      contrasena: json['contrasena'],
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
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento?.toIso8601String(),
      'direccion': direccion,
      'horaInicio': '${horaInicio?.hour}:${horaInicio?.minute}',
      'horaFin': '${horaFin?.hour}:${horaFin?.minute}',
      'tipoTrabajador': tipoTrabajador?.name,
      'activo': activo,
    };
  }
}
