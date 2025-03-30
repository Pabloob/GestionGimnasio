import 'package:frontend/models/enums.dart';

class ModificacionClaseGetDTO {
  final int? id;
  final int? claseId;
  final String? claseNombre;
  final double? precio;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final int? instructorId;
  final String? instructorNombre;
  final int? totalInscritos;
  final int? capacidadMaxima;
  final String? sala;
  final Set<Dia>? dias;
  final DateTime? fechaModificacion;
  final String? comentario;
  final int? modificadoPorId;
  final String? modificadoPorNombre;

  ModificacionClaseGetDTO({
    this.id,
    this.claseId,
    this.claseNombre,
    this.precio,
    this.horaInicio,
    this.horaFin,
    this.instructorId,
    this.instructorNombre,
    this.totalInscritos,
    this.capacidadMaxima,
    this.sala,
    this.dias,
    this.fechaModificacion,
    this.comentario,
    this.modificadoPorId,
    this.modificadoPorNombre,
  });

  // Factory para crear una instancia desde JSON
  factory ModificacionClaseGetDTO.fromJson(Map<String, dynamic> json) {
    return ModificacionClaseGetDTO(
      id: json['id'],
      claseId: json['claseId'],
      claseNombre: json['claseNombre'],
      precio: json['precio']?.toDouble(),
      horaInicio:
          json['horaInicio'] != null
              ? DateTime.parse(json['horaInicio']) // Suponemos formato ISO8601
              : null,
      horaFin:
          json['horaFin'] != null
              ? DateTime.parse(json['horaFin']) // Suponemos formato ISO8601
              : null,
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
      comentario: json['comentario'],
      modificadoPorId: json['modificadoPorId'],
      modificadoPorNombre: json['modificadoPorNombre'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'claseId': claseId,
      'claseNombre': claseNombre,
      'precio': precio,
      'horaInicio': horaInicio?.toIso8601String(),
      'horaFin': horaFin?.toIso8601String(),
      'instructorId': instructorId,
      'instructorNombre': instructorNombre,
      'totalInscritos': totalInscritos,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias?.map((d) => d.toString().split('.').last).toList(),
      'fechaModificacion': fechaModificacion?.toIso8601String(),
      'comentario': comentario,
      'modificadoPorId': modificadoPorId,
      'modificadoPorNombre': modificadoPorNombre,
    };
  }
}
