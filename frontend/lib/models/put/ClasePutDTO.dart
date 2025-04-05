class ClasePutDTO {
  final String? nombre;
  final int? capacidadMaxima;
  final double? precio;
  final String? descripcion;
  final bool? activa;

  ClasePutDTO({
    this.nombre,
    this.capacidadMaxima,
    this.precio,
    this.descripcion,
    this.activa,
  });

  // Factory para crear una instancia desde JSON
  factory ClasePutDTO.fromJson(Map<String, dynamic> json) {
    return ClasePutDTO(
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
