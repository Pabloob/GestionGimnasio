
import 'package:frontend/models/enums.dart';

class ModificacionClasePutDTO {
  final int id;
  final double? precio;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final int? instructorId;
  final int? capacidadMaxima;
  final String? sala;
  final Set<Dia>? dias;
  final String? comentario;
  final int? modificadoPor;

  ModificacionClasePutDTO({
    required this.id,
    this.precio,
    this.horaInicio,
    this.horaFin,
    this.instructorId,
    this.capacidadMaxima,
    this.sala,
    this.dias,
    this.comentario,
    this.modificadoPor,
  });

  // Factory para crear una instancia desde JSON
  factory ModificacionClasePutDTO.fromJson(Map<String, dynamic> json) {
    return ModificacionClasePutDTO(
      id: json['id'],
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
      capacidadMaxima: json['capacidadMaxima'],
      sala: json['sala'],
      dias:
          (json['dias'] as List)
              .map(
                (d) => Dia.values.firstWhere((e) => e.toString() == 'Dia.$d'),
              )
              .toSet(),
      comentario: json['comentario'],
      modificadoPor: json['modificadoPor'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'precio': precio,
      'horaInicio': horaInicio?.toIso8601String(),
      'horaFin': horaFin?.toIso8601String(),
      'instructorId': instructorId,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias?.map((d) => d.toString().split('.').last).toList(),
      'comentario': comentario,
      'modificadoPor': modificadoPor,
    };
  }
}
