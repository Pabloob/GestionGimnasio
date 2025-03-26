enum EstadoPago { PENDIENTE, COMPLETADO, CANCELADO }

class Pago {
  final int idCliente;
  final double monto;
  final DateTime? fechaPago;
  final EstadoPago estadoPago;
  final String? comentario;

  Pago({
    required this.idCliente,
    required this.monto,
    this.fechaPago,
    EstadoPago? estadoPago,
    this.comentario,
  }) : estadoPago = estadoPago ?? EstadoPago.PENDIENTE;

  factory Pago.fromJson(Map<String, dynamic> json) {
    return Pago(
      idCliente: json['idCliente'],
      monto: json['monto'].toDouble(),
      fechaPago: json['fechaPago'] != null ? DateTime.parse(json['fechaPago']) : null,
      estadoPago: EstadoPago.values.firstWhere(
        (e) => e.toString() == 'EstadoPago.${json['estadoPago']}',
        orElse: () => EstadoPago.PENDIENTE
      ),
      comentario: json['comentario'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idCliente': idCliente,
      'monto': monto,
      'fechaPago': fechaPago?.toIso8601String(),
      'estadoPago': estadoPago.toString().split('.').last,
      'comentario': comentario,
    };
  }
}