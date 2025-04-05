class SalaGetDTO {
  final int id;
  final String nombre;

  SalaGetDTO({required this.id, required this.nombre});

  // Factory para crear una instancia desde JSON
  factory SalaGetDTO.fromJson(Map<String, dynamic> json) {
    return SalaGetDTO(id: json['id'], nombre: json['nombre']);
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'id': id, 'nombre': nombre};
  }
}
