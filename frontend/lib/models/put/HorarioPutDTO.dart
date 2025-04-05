import 'package:frontend/models/enums.dart';

class HorarioPutDTO {
  final int? claseId;
  final DiaSemana? diaSemana;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final int? salaId;
  final int? instructorId;
  final DateTime fechasInicio;
  final DateTime fechaFin;

  HorarioPutDTO({
    this.claseId,
    this.diaSemana,
    this.horaInicio,
    this.horaFin,
    this.salaId,
    this.instructorId,
    required this.fechasInicio,
    required this.fechaFin,
  });

  // Factory para crear una instancia desde JSON
  factory HorarioPutDTO.fromJson(Map<String, dynamic> json) {
    return HorarioPutDTO(
      claseId: json['id'],
      diaSemana: DiaSemana.fromName(json['diaSemana']),
      horaInicio: DateTime.parse(json['horaInicio']),
      horaFin: DateTime.parse(json['horaFin']),
      salaId: json['sala'],
      instructorId: json['instructorId'],
      fechasInicio: DateTime.parse(json['fechasInicio']),
      fechaFin: DateTime.parse(json['fechaFin']),
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': claseId,
      'dias': diaSemana?.value,
      'horaInicio': horaInicio?.toIso8601String(),
      'horaFin': horaFin?.toIso8601String(),
      'sala': salaId,
      'instructorId': instructorId,
      'fechasInicio': fechasInicio.toIso8601String(),
      'fechaFin': fechaFin.toIso8601String(),
    };
  }
}
