import 'package:frontend/models/enums.dart';

class ModificacionClasePostDTO {
  final int claseId;
  final double precio;
  final DateTime horaInicio; 
  final DateTime horaFin; 
  final int instructorId;
  final int capacidadMaxima;
  final String sala;
  final Set<Dia> dias; 
  final String? comentario;
  final int modificadoPor;

  ModificacionClasePostDTO({
    required this.claseId,
    required this.precio,
    required this.horaInicio,
    required this.horaFin,
    required this.instructorId,
    required this.capacidadMaxima,
    required this.sala,
    required this.dias,
    this.comentario,
    required this.modificadoPor,
  });

  // Factory para crear una instancia desde JSON
  factory ModificacionClasePostDTO.fromJson(Map<String, dynamic> json) {
    return ModificacionClasePostDTO(
      claseId: json['claseId'],
      precio: json['precio'].toDouble(),
      horaInicio: DateTime.parse(
        json['horaInicio'],
      ), 
      horaFin: DateTime.parse(json['horaFin']),
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
      'claseId': claseId,
      'precio': precio,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'instructorId': instructorId,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias.map((d) => d.toString().split('.').last).toList(),
      'comentario': comentario,
      'modificadoPor': modificadoPor,
    };
  }
}
