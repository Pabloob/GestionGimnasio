import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';

class InscripcionGetDTO {
  final int id;
  final ClienteGetDTO cliente;
  final ClaseGetDTO clase;
  final DateTime fechaRegistro;
  final bool asistio;

  InscripcionGetDTO({
    required this.id,
    required this.cliente,
    required this.clase,
    required this.fechaRegistro,
    required this.asistio,
  });

  // Factory para crear una instancia desde JSON
  factory InscripcionGetDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionGetDTO(
      id: json['id'],
      cliente: ClienteGetDTO.fromJson(json['cliente']),
      clase: ClaseGetDTO.fromJson(json['clase']),
      fechaRegistro: DateTime.parse(json['fechaRegistro']),
      asistio: json['asistio'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'cliente': cliente.toJson(),
      'clase': clase.toJson(),
      'fechaRegistro': fechaRegistro.toIso8601String(),
      'asistio': asistio,
    };
  }
}
