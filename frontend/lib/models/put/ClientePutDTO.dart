import 'package:frontend/models/put/UsuarioPutDTO.dart';

class ClientePutDTO {
  final UsuarioPutDTO? usuarioPutDTO;

  ClientePutDTO({this.usuarioPutDTO});

  // Factory para crear una instancia desde JSON
  factory ClientePutDTO.fromJson(Map<String, dynamic> json) {
    return ClientePutDTO(
      usuarioPutDTO:
          json['usuarioPutDTO'] != null
              ? UsuarioPutDTO.fromJson(json['usuarioPutDTO'])
              : null,
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'usuarioPutDTO': usuarioPutDTO?.toJson()};
  }
}
