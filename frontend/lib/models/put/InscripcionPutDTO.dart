class InscripcionPutDTO {
  final int? clienteId;
  final int? claseId;
  final bool? asistio;

  InscripcionPutDTO({this.clienteId, this.claseId, this.asistio});

  // Factory para crear una instancia desde JSON
  factory InscripcionPutDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionPutDTO(
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
