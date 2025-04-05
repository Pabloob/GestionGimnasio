class ClaseGetDTO {
  final int id;
  final String nombre;
  final int capacidadMaxima;
  final double precio;
  final String descripcion;
  final bool activa;

  ClaseGetDTO({
    required this.id,
    required this.nombre,
    required this.capacidadMaxima,
    required this.precio,
    required this.descripcion,
    required this.activa,
  });

  // Factory para crear una instancia desde JSON
  factory ClaseGetDTO.fromJson(Map<String, dynamic> json) {
    return ClaseGetDTO(
      id: json['id'],
      nombre: json['nombre'],
      capacidadMaxima: json['capacidadMaxima'],
      precio: json['precio']?.toDouble(),
      descripcion: json['descripcion'],
      activa: json['activa'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nombre': nombre,
      'capacidadMaxima': capacidadMaxima,
      'precio': precio,
      'descripcion': descripcion,
      'activa': activa,
    };
  }
}
