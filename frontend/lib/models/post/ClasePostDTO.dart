import 'package:frontend/models/enums.dart';
import 'package:intl/intl.dart';

class ClasePostDTO {
  final String nombre;
  final double precio;
  final DateTime horaInicio;
  final DateTime horaFin;
  final int instructorId;
  final int capacidadMaxima;
  final String sala;
  final Set<Dia> dias;

  ClasePostDTO({
    required this.nombre,
    required this.precio,
    required this.horaInicio,
    required this.horaFin,
    required this.instructorId,
    required this.capacidadMaxima,
    required this.sala,
    required this.dias,
  });

  // Factory para crear una instancia desde JSON
  factory ClasePostDTO.fromJson(Map<String, dynamic> json) {
    return ClasePostDTO(
      nombre: json['nombre'],
      precio: json['precio'].toDouble(),
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
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'precio': precio,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'instructorId': instructorId,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias.map((d) => d.toString().split('.').last).toList(),
    };
  }
}
