class InscripcionPostDTO {
  final int clienteId;
  final int claseId;
  final bool asistio;

  InscripcionPostDTO({
    required this.clienteId,
    required this.claseId,
    required this.asistio,
  });

  // Factory para crear una instancia desde JSON
  factory InscripcionPostDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionPostDTO(
      clienteId: json['clienteId'],
      claseId: json['claseId'],
      asistio: json['asistio'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'clienteId': clienteId, 'claseId': claseId, 'asistio': asistio};
  }
}
