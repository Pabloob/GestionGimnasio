class SalaPutDTO {
  final String? nombre;

  SalaPutDTO({this.nombre});

  // Factory para crear una instancia desde JSON
  factory SalaPutDTO.fromJson(Map<String, dynamic> json) {
    return SalaPutDTO(nombre: json['nombre']);
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'nombre': nombre};
  }
}
