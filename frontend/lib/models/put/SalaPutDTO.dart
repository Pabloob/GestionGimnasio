class RoomPutDTO {
  final String? nombre;

  RoomPutDTO({this.nombre});

  // Factory para crear una instancia desde JSON
  factory RoomPutDTO.fromJson(Map<String, dynamic> json) {
    return RoomPutDTO(nombre: json['nombre']);
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'nombre': nombre};
  }
}
