class ClientePostDTO {
  final String nombre;
  final String contrasena;
  final String correo;
  final String telefono;
  final DateTime fechaNacimiento;
  final bool activo;

  ClientePostDTO({
    required this.nombre,
    required this.contrasena,
    required this.correo,
    required this.telefono,
    required this.fechaNacimiento,
    required this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory ClientePostDTO.fromJson(Map<String, dynamic> json) {
    return ClientePostDTO(
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaNacimiento: DateTime.parse(json['fechaNacimiento']),
      activo: json['activo'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento.toIso8601String(),
      'activo': activo,
    };
  }
}