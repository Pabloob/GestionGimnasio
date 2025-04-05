import 'package:frontend/models/post/UsuarioPostDTO.dart';

class ClientePostDTO {
  final UsuarioPostDTO usuarioPostDTO;

  ClientePostDTO({required this.usuarioPostDTO});

  // Factory para crear una instancia desde JSON
  factory ClientePostDTO.fromJson(Map<String, dynamic> json) {
    return ClientePostDTO(usuarioPostDTO: UsuarioPostDTO.fromJson(json['usuario']));
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'usuarioPostDTO': usuarioPostDTO.toJson()};
  }
  
}
