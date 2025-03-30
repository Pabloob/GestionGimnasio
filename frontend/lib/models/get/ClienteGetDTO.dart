class ClienteGetDTO {
  final int? id;
  final String? nombre;
  final String? correo;
  final String? telefono;
  final DateTime? fechaNacimiento;
  final int? inasistencias;
  final bool? activo;
  final List<int>? inscripcionesIds;

  ClienteGetDTO({
    this.id,
    this.nombre,
    this.correo,
    this.telefono,
    this.fechaNacimiento,
    this.inasistencias,
    this.activo,
    this.inscripcionesIds,
  });

  // Factory para crear una instancia desde JSON
  factory ClienteGetDTO.fromJson(Map<String, dynamic> json) {
    return ClienteGetDTO(
      id: json['id'],
      nombre: json['nombre'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaNacimiento: DateTime.parse(json['fechaNacimiento']),
      inasistencias: json['inasistencias'],
      activo: json['activo'],
      inscripcionesIds: json['inscripcionesIds'] != null
          ? List<int>.from(json['inscripcionesIds'])
          : null,
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento?.toIso8601String(),
      'inasistencias': inasistencias,
      'activo': activo,
      'inscripcionesIds': inscripcionesIds,
    };
  }
}