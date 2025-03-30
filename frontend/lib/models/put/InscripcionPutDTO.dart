class InscripcionPutDTO {
  final int id;
  final int? clienteId;
  final int? claseId;
  final int? pagoId;
  final bool? estadoPago;

  InscripcionPutDTO({
    required this.id,
    this.clienteId,
    this.claseId,
    this.pagoId,
    this.estadoPago,
  });

  // Factory para crear una instancia desde JSON
  factory InscripcionPutDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionPutDTO(
      id: json['id'],
      clienteId: json['clienteId'],
      claseId: json['claseId'],
      pagoId: json['pagoId'],
      estadoPago: json['estadoPago'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clienteId': clienteId,
      'claseId': claseId,
      'pagoId': pagoId,
      'estadoPago': estadoPago,
    };
  }
}