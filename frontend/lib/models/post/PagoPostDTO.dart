class PagoPostDTO {
  final int clienteId;
  final double monto;
  final bool pagado;

  PagoPostDTO({
    required this.clienteId,
    required this.monto,
    required this.pagado,
  });

  // Factory para crear una instancia desde JSON
  factory PagoPostDTO.fromJson(Map<String, dynamic> json) {
    return PagoPostDTO(
      clienteId: json['clienteId'],
      monto: json['monto'],
      pagado: json['pagado'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'clienteId': clienteId,
      'monto': monto,
      'pagado': pagado
    };
  }
}
