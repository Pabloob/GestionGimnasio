
class ClasePostDTO {
  final String nombre;
  final int capacidadMaxima;
  final double precio;
  final String descripcion;
  final bool activa;

  ClasePostDTO({
    required this.nombre,
    required this.capacidadMaxima,
    required this.precio,
    required this.descripcion,
    required this.activa,
  });

  // Factory para crear una instancia desde JSON
  factory ClasePostDTO.fromJson(Map<String, dynamic> json) {
    return ClasePostDTO(
      nombre: json['nombre'],
      precio: json['precio'].toDouble(),
      capacidadMaxima: json['capacidadMaxima'],
      descripcion: json['descripcion'],
      activa: json['activa'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'precio': precio,
      'capacidadMaxima': capacidadMaxima,
      'descripcion': descripcion,
      'activa': activa,
    };
  }
}
