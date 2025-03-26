import 'package:flutter/material.dart';

enum TipoUsuario { CLIENTE, TRABAJADOR }

enum TipoTrabajador { ADMIN, RECEPCIONISTA, INSTRUCTOR }

class Trabajador {
  final String nombre;
  final String contrasena;
  final String correo;
  final String telefono;
  final DateTime fechaDeNacimiento;
  final DateTime fechaRegistro;
  final TipoUsuario tipoUsuario;
  final bool activo;
  final String direccion;
  final TimeOfDay  horaInicio;
  final TimeOfDay  horaFin;
  final TipoTrabajador tipoTrabajador;

  Trabajador({
    required this.nombre,
    required this.contrasena,
    required this.correo,
    required this.telefono,
    required this.fechaDeNacimiento,
    required this.direccion,
    required this.horaInicio,
    required this.horaFin,
    required this.tipoTrabajador,
    DateTime? fechaRegistro,
    TipoUsuario? tipoUsuario,
    bool? activo,
  }) : fechaRegistro = fechaRegistro ?? DateTime.now(),
       tipoUsuario =
           tipoUsuario ??
           TipoUsuario.TRABAJADOR,
       activo = activo ?? true;

  factory Trabajador.fromJson(Map<String, dynamic> json) {
    return Trabajador(
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaDeNacimiento: DateTime.parse(json['fechaDeNacimiento']),
      direccion: json['direccion'],
      horaInicio: _parseTimeOfDay(json['horaInicio']),
      horaFin: _parseTimeOfDay(json['horaFin']),
      tipoTrabajador: TipoTrabajador.values.firstWhere(
        (e) => e.toString() == 'TipoTrabajador.${json['tipoTrabajador']}',
      ),
      fechaRegistro:
          json['fechaRegistro'] != null
              ? DateTime.parse(json['fechaRegistro'])
              : null,
      tipoUsuario:
          json['tipoUsuario'] != null
              ? TipoUsuario.values.firstWhere(
                (e) => e.toString() == 'TipoUsuario.${json['tipoUsuario']}',
              )
              : null,
      activo: json['activo'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaDeNacimiento': fechaDeNacimiento.toIso8601String(),
      'direccion': direccion,
      'horaInicio': '${horaInicio.hour}:${horaInicio.minute}',
      'horaFin': '${horaFin.hour}:${horaFin.minute}',
      'tipoTrabajador': tipoTrabajador,
      'fechaRegistro': fechaRegistro.toIso8601String(),
      'tipoUsuario': tipoUsuario,
      'activo': activo,
    };
  }

  static TimeOfDay _parseTimeOfDay(String hora) {
    List<String> partes = hora.split(":");
    return TimeOfDay(
      hour: int.parse(partes[0]),
      minute: int.parse(partes[1]),
    );
  }
}
