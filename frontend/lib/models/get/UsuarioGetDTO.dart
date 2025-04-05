import 'package:frontend/models/enums.dart';

class UsuarioGetDTO {
  final int id;
  final String nombre;
  final String correo;
  final String telefono;
  final DateTime fechaNacimiento;
  final DateTime fechaRegistro;
  final TipoUsuario tipoUsuario;
  final bool activo;

  UsuarioGetDTO({
    required this.id,
    required this.nombre,
    required this.correo,
    required this.telefono,
    required this.fechaNacimiento,
    required this.fechaRegistro,
    required this.tipoUsuario,
    required this.activo,
  });

  // Factory para crear una instancia desde JSON
  factory UsuarioGetDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioGetDTO(
      id: json['id'],
      nombre: json['nombre'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaNacimiento: DateTime.parse(json['fechaNacimiento']),
      fechaRegistro: DateTime.parse(json['fechaRegistro']),
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
      'correo': correo,
      'telefono': telefono,
      'fechaNacimiento': fechaNacimiento.toIso8601String(),
      'fechaRegistro': fechaRegistro.toIso8601String(),
      'tipoUsuario': tipoUsuario.name,
      'activo': activo,
    };
  }
}
