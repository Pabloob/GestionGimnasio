import 'package:frontend/models/enums.dart';

class HorarioPostDTO {
  final int claseId;
  final DiaSemana diaSemana;
  final DateTime horaInicio;
  final DateTime horaFin;
  final int salaId;
  final int instructorId;
  final DateTime fechasInicio;
  final DateTime fechaFin;

  HorarioPostDTO({
    required this.claseId,
    required this.diaSemana,
    required this.horaInicio,
    required this.horaFin,
    required this.salaId,
    required this.instructorId,
    required this.fechasInicio,
    required this.fechaFin,
  });

  // Factory para crear una instancia desde JSON
  factory HorarioPostDTO.fromJson(Map<String, dynamic> json) {
    return HorarioPostDTO(
      claseId: json['claseId'],
      diaSemana: DiaSemana.fromName(json['diaSemana']),
      horaInicio: DateTime.parse(json['horaInicio']),
      horaFin: DateTime.parse(json['horaFin']),
      salaId: json['salaId'],
      instructorId: json['instructorId'],
      fechasInicio: DateTime.parse(json['fechasInicio']),
      fechaFin: DateTime.parse(json['fechaFin']),
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'claseId': claseId,
      'diaSemana': diaSemana.value,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'salaId': salaId,
      'instructorId': instructorId,
      'fechasInicio': fechasInicio.toIso8601String(),
      'fechaFin': fechaFin.toIso8601String(),
    };
  }
}
