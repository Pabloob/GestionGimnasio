import 'package:flutter/material.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/utils/utils.dart';

class TrabajadorPostDTO {
  final String nombre;
  final String contrasena;
  final String correo;
  final String telefono;
  final DateTime fechaNacimiento;
  final String direccion;
  final TimeOfDay? horaInicio;
  final TimeOfDay? horaFin;
  final TipoTrabajador tipoTrabajador;

  TrabajadorPostDTO({
    required this.nombre,
    required this.contrasena,
    required this.correo,
    required this.telefono,
    required this.fechaNacimiento,
    required this.direccion,
    required this.horaInicio,
    required this.horaFin,
    required this.tipoTrabajador,
  });

  // Factory para crear una instancia desde JSON
  factory TrabajadorPostDTO.fromJson(Map<String, dynamic> json) {
    return TrabajadorPostDTO(
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
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento.toIso8601String(),
      'direccion': direccion,
      'horaInicio': '${horaInicio?.hour}:${horaInicio?.minute}',
      'horaFin': '${horaFin?.hour}:${horaFin?.minute}',
      'tipoTrabajador': tipoTrabajador.name,
    };
  }
}
