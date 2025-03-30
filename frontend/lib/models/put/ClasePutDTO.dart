import 'package:frontend/models/enums.dart';
import 'package:intl/intl.dart';

class ClasePutDTO {
  final int id;
  final String? nombre;
  final double? precio;
  final DateTime? horaInicio;
  final DateTime? horaFin;
  final int? instructorId;
  final int? capacidadMaxima;
  final String? sala;
  final Set<Dia>? dias;
  final bool? activa;

  ClasePutDTO({
    required this.id,
    this.nombre,
    this.precio,
    this.horaInicio,
    this.horaFin,
    this.instructorId,
    this.capacidadMaxima,
    this.sala,
    this.dias,
    this.activa,
  });

  // Factory para crear una instancia desde JSON
  factory ClasePutDTO.fromJson(Map<String, dynamic> json) {
    return ClasePutDTO(
      id: json['id'],
      nombre: json['nombre'],
      precio: json['precio']?.toDouble(),
      horaInicio: DateFormat("HH:mm:ss").parse(json['horaInicio']),
      horaFin: DateFormat("HH:mm:ss").parse(json['horaFin']),
      instructorId: json['instructorId'],
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
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias?.map((d) => d.toString().split('.').last).toList(),
      'activa': activa,
    };
  }
}
