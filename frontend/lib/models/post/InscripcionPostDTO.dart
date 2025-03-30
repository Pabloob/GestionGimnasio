class InscripcionPostDTO {
  final int clienteId;
  final int claseId;
  final int? pagoId;

  InscripcionPostDTO({
    required this.clienteId,
    required this.claseId,
    this.pagoId,
  });

  // Factory para crear una instancia desde JSON
  factory InscripcionPostDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionPostDTO(
      clienteId: json['clienteId'],
      claseId: json['claseId'],
      pagoId: json['pagoId'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'clienteId': clienteId,
      'claseId': claseId,
      'pagoId': pagoId,
    };
  }
}