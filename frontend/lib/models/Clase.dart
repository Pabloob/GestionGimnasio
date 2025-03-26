import 'package:intl/intl.dart';

enum Dia {
  LUNES, 
  MARTES, 
  MIERCOLES, 
  JUEVES, 
  VIERNES, 
  SABADO, 
  DOMINGO
}

class Clase {
  final String nombre;
  final double precio;
  final DateTime horaInicio;
  final DateTime horaFin;
  final int idInstructor;
  final int capacidadMaxima;
  final String sala;
  final Set<Dia> dias;
  final bool activa;

  Clase({
    required this.nombre,
    required this.precio,
    required this.horaInicio,
    required this.horaFin,
    required this.idInstructor,
    required this.capacidadMaxima,
    required this.sala,
    required this.dias,
    bool? activa,
  }) : activa = activa ?? true;

  factory Clase.fromJson(Map<String, dynamic> json) {
    return Clase(
      nombre: json['nombre'],
      precio: json['precio'].toDouble(),
      horaInicio: DateFormat("HH:mm:ss").parse(json['horaInicio']),
      horaFin: DateFormat("HH:mm:ss").parse(json['horaFin']),
      idInstructor: json['idInstructor'],
      capacidadMaxima: json['capacidadMaxima'],
      sala: json['sala'],
      dias: (json['dias'] as List).map((d) => Dia.values.firstWhere((e) => e.toString() == 'Dia.$d')).toSet(),
      activa: json['activa'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'precio': precio,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'idInstructor': idInstructor,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'dias': dias.map((d) => d.toString().split('.').last).toList(),
      'activa': activa,
    };
  }
}