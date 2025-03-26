enum Dia {
  LUNES, 
  MARTES, 
  MIERCOLES, 
  JUEVES, 
  VIERNES, 
  SABADO, 
  DOMINGO
}

class ModificacionClase {
  final int idClase;
  final double precio;
  final DateTime horaInicio;
  final DateTime horaFin;
  final int idInstructor;
  final int capacidadMaxima;
  final String sala;
  final DateTime fechaModificacion;
  final Set<Dia> dias;
  final String? comentario;

  ModificacionClase({
    required this.idClase,
    required this.precio,
    required this.horaInicio,
    required this.horaFin,
    required this.idInstructor,
    required this.capacidadMaxima,
    required this.sala,
    DateTime? fechaModificacion,
    required this.dias,
    this.comentario,
  }) : fechaModificacion = fechaModificacion ?? DateTime.now();

  factory ModificacionClase.fromJson(Map<String, dynamic> json) {
    return ModificacionClase(
      idClase: json['idClase'],
      precio: json['precio'].toDouble(),
      horaInicio: DateTime.parse(json['horaInicio']),
      horaFin: DateTime.parse(json['horaFin']),
      idInstructor: json['idInstructor'],
      capacidadMaxima: json['capacidadMaxima'],
      sala: json['sala'],
      fechaModificacion: json['fechaModificacion'] != null 
          ? DateTime.parse(json['fechaModificacion']) 
          : null,
      dias: (json['dias'] as List).map((d) => Dia.values.firstWhere((e) => e.toString() == 'Dia.$d')).toSet(),
      comentario: json['comentario'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idClase': idClase,
      'precio': precio,
      'horaInicio': horaInicio.toIso8601String(),
      'horaFin': horaFin.toIso8601String(),
      'idInstructor': idInstructor,
      'capacidadMaxima': capacidadMaxima,
      'sala': sala,
      'fechaModificacion': fechaModificacion.toIso8601String(),
      'dias': dias.map((d) => d.toString().split('.').last).toList(),
      'comentario': comentario,
    };
  }
}