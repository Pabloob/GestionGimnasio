class ClientePutDTO {
  int id;
  String? nombre;
  String? contrasena;
  String? correo;
  String? telefono;
  int? inasistencias;
  bool? activo;

  ClientePutDTO({
    required this.id,
    this.nombre,
    this.contrasena,
    this.correo,
    this.telefono,
    this.inasistencias,
    this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory ClientePutDTO.fromJson(Map<String, dynamic> json) {
    return ClientePutDTO(
      id: json['id'],
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      inasistencias: json['inasistencias'],
      activo: json['activo'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'inasistencias': inasistencias,
      'activo': activo,
    };
  }
}
