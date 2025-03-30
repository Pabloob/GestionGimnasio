class InscripcionGetDTO {
  final int? id;
  final int? clienteId;
  final String? clienteNombre;
  final int? claseId;
  final String? claseNombre;
  final int? pagoId;
  final DateTime? fechaRegistro;
  final bool? estadoPago;

  InscripcionGetDTO({
    this.id,
    this.clienteId,
    this.clienteNombre,
    this.claseId,
    this.claseNombre,
    this.pagoId,
    this.fechaRegistro,
    this.estadoPago,
  });

  // Factory para crear una instancia desde JSON
  factory InscripcionGetDTO.fromJson(Map<String, dynamic> json) {
    return InscripcionGetDTO(
      id: json['id'],
      clienteId: json['clienteId'],
      clienteNombre: json['clienteNombre'],
      claseId: json['claseId'],
      claseNombre: json['claseNombre'],
      pagoId: json['pagoId'],
      fechaRegistro: json['fechaRegistro'] != null
          ? DateTime.parse(json['fechaRegistro'])
          : null,
      estadoPago: json['estadoPago'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clienteId': clienteId,
      'clienteNombre': clienteNombre,
      'claseId': claseId,
      'claseNombre': claseNombre,
      'pagoId': pagoId,
      'fechaRegistro': fechaRegistro?.toIso8601String(),
      'estadoPago': estadoPago,
    };
  }
}