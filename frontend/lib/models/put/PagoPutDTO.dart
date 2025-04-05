class PagoPutDTO {
  final int? clienteId;
  final double? monto;
  final bool? pagado;

  PagoPutDTO({this.clienteId, this.monto, this.pagado});

  // Factory para crear una instancia desde JSON
  factory PagoPutDTO.fromJson(Map<String, dynamic> json) {
    return PagoPutDTO(
      clienteId: json['clienteId'],
      monto: json['monto'],
      pagado: json['pagado'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {'clienteId': clienteId, 'monto': monto, 'pagado': pagado};
  }
}
