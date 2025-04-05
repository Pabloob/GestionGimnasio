import 'package:frontend/models/enums.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/get/SalaGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:intl/intl.dart';

class HorarioGetDTO {
  final int id;
  final ClaseGetDTO clase;
  final DiaSemana diaSemana;
  final DateTime horaInicio;
  final DateTime horaFin;
  final SalaGetDTO sala;
  final TrabajadorGetDTO instructor;
  final DateTime fechasInicio;
  final DateTime fechaFin;

  HorarioGetDTO({
    required this.id,
    required this.clase,
    required this.diaSemana,
    required this.horaInicio,
    required this.horaFin,
    required this.sala,
    required this.instructor,
    required this.fechasInicio,
    required this.fechaFin,
  });

  factory HorarioGetDTO.fromJson(Map<String, dynamic> json) {
    return HorarioGetDTO(
      id: json['id'],
      clase: ClaseGetDTO.fromJson(json['clase']),
      diaSemana: DiaSemana.fromName(json['diaSemana']),
      horaInicio: DateFormat("HH:mm:ss").parse(json['horaInicio']),
      horaFin: DateFormat("HH:mm:ss").parse(json['horaFin']),
      sala: SalaGetDTO.fromJson(json['sala']),
      instructor: TrabajadorGetDTO.fromJson(json['instructor']),
      fechasInicio: DateTime.parse(json['fechasInicio']),
      fechaFin: DateTime.parse(json['fechaFin']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clase': clase.toJson(),
      'diaSemana': diaSemana.value,
      'horaInicio': DateFormat("HH:mm:ss").format(horaInicio),
      'horaFin': DateFormat("HH:mm:ss").format(horaFin),
      'sala': sala.toJson(),
      'instructor': instructor.toJson(),
      'fechasInicio': fechasInicio.toIso8601String(),
      'fechaFin': fechaFin.toIso8601String(),
    };
  }

  String getHoras() {
    return "Horario: ${horaInicio.hour}:${horaInicio.minute} - ${horaFin.hour}:${horaFin.minute}";
  }

  String getFechas() {
    return "Fecha: $fechasInicio - $fechaFin";
  }
}
