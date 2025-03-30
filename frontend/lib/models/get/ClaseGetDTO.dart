import 'dart:convert';

import 'package:frontend/models/enums.dart';
import 'package:intl/intl.dart';

class ClaseGetDTO {
  final int? id;
  final String? nombre;
  final double? precio;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final int? instructorId;
  final String? instructorNombre;
  final int? totalInscritos;
  final int? capacidadMaxima;
  final String? sala;
  final Set<Dia>? dias;
  final bool? activa;

  ClaseGetDTO({
    this.id,
    this.nombre,
    this.precio,
    this.horaInicio,
    this.horaFin,
    this.instructorId,
    this.instructorNombre,
    this.totalInscritos,
    this.capacidadMaxima,
    this.sala,
    this.dias,
    this.activa,
  });

  // Factory para crear una instancia desde JSON
  factory ClaseGetDTO.fromJson(Map<String, dynamic> json) {
    return ClaseGetDTO(
      id: json['id'],
      nombre: json['nombre'] != null ? utf8.decode(json['nombre'].runes.toList()) : null,
      precio: json['precio']?.toDouble(),
      horaInicio: DateFormat("HH:mm:ss").parse(json['horaInicio']),
      horaFin: DateFormat("HH:mm:ss").parse(json['horaFin']),
      instructorId: json['instructorId'],
      instructorNombre: json['instructorNombre'],
      totalInscritos: json['totalInscritos'],
      capacidadMaxima: json['capacidadMaxima'],
      sala: json['sala'],
      dias:
          (json['dias'] as List)
              .map(
                (d) => Dia.values.firstWhere((e) => e.toString() == 'Dia.$d'),
              )
              .toSet(),
      activa: json['activa'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'precio': precio,
      'horaInicio': horaInicio?.toIso8601String(),
      'horaFin': horaFin?.toIso8601String(),
      'instructorId': instructorId,
      'instructorNombre': instructorNombre,
      'totalInscritos': totalInscritos,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias?.map((d) => d.toString().split('.').last).toList(),
      'activa': activa,
    };
  }

  String? getDias() {
    Map<Dia, String> abreviaciones = {
      Dia.LUNES: "L",
      Dia.MARTES: "M",
      Dia.MIERCOLES: "X",
      Dia.JUEVES: "J",
      Dia.VIERNES: "V",
      Dia.SABADO: "S",
      Dia.DOMINGO: "D",
    };

    return dias?.map((dia) => abreviaciones[dia]!).join(",");
  }
}
