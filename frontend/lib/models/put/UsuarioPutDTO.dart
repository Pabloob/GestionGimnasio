import 'package:frontend/models/enums.dart';

class UsuarioPutDTO {
  final int id;
  final String? nombre;
  final String? contrasena;
  final String? correo;
  final String? telefono;
  final DateTime? fechaDeNacimiento;
  final TipoUsuario? tipoUsuario;
  final bool? activo;

  UsuarioPutDTO({
    required this.id,
    this.nombre,
    this.contrasena,
    this.correo,
    this.telefono,
    this.fechaDeNacimiento,
    this.tipoUsuario,
    this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory UsuarioPutDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioPutDTO(
      id: json['id'],
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
      'id': id,
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaDeNacimiento': fechaDeNacimiento?.toIso8601String(),
      'tipoUsuario': tipoUsuario?.name,
      'activo': activo,
    };
  }
}
