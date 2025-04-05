class SalaPostDTO {
  final String nombre;

  SalaPostDTO({required this.nombre});

  // Factory para crear una instancia desde JSON
  factory SalaPostDTO.fromJson(Map<String, dynamic> json) {
    return SalaPostDTO(nombre: json['nombre']);
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'nombre': nombre};
  }
}
