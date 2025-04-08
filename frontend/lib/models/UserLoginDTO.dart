class UsuarioLoginDTO {
  final String correo;
  final String contrasena;

  UsuarioLoginDTO({required this.correo, required this.contrasena});

  // Factory para crear una instancia desde JSON
  factory UsuarioLoginDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioLoginDTO(
      correo: json['correo'],
      contrasena: json['contrasena'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'correo': correo, 'contrasena': contrasena};
  }
}
