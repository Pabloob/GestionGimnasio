class UsuarioPutDTO {
  final String? nombre;
  final String? contrasena;
  final String? correo;
  final String? telefono;
  final bool? activo;

  UsuarioPutDTO({
    this.nombre,
    this.contrasena,
    this.correo,
    this.telefono,
    this.activo = true,
  });

  // Factory para crear una instancia desde JSON
  factory UsuarioPutDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioPutDTO(
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      activo: json['activo'] ?? true,
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'activo': activo,
    };
  }
}
