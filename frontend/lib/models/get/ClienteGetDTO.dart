import 'package:frontend/models/get/UsuarioGetDTO.dart';

class ClienteGetDTO {
  final UsuarioGetDTO usuario;

  ClienteGetDTO({required this.usuario});

  // Factory para crear una instancia desde JSON
  factory ClienteGetDTO.fromJson(Map<String, dynamic> json) {
    return ClienteGetDTO(
      usuario: UsuarioGetDTO.fromJson(json['usuario'] as Map<String, dynamic>),
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'usuario': usuario.toJson(),
    };
  }
}
