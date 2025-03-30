import 'package:frontend/models/enums.dart';

class UsuarioPostDTO {
  final String nombre;
  final String contrasena;
  final String correo;
  final String telefono;
  final DateTime fechaDeNacimiento;
  final TipoUsuario tipoUsuario;
  final bool activo;

  UsuarioPostDTO({
    required this.nombre,
    required this.contrasena,
    required this.correo,
    required this.telefono,
    required this.fechaDeNacimiento,
    required this.tipoUsuario,
    required this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory UsuarioPostDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioPostDTO(
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaDeNacimiento: DateTime.parse(json['fechaNacimiento']),
      tipoUsuario: TipoUsuario.values.firstWhere(
        (e) => e.name == json["tipoUsuario"],
      ),
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
      'fechaDeNacimiento': fechaDeNacimiento.toIso8601String(),
      'tipoUsuario': tipoUsuario.name,
      'activo': activo,
    };
  }
}
