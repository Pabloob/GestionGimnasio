import 'package:frontend/models/get/ClienteGetDTO.dart';

class PagoGetDTO {
  final int id;
  final ClienteGetDTO cliente;
  final double monto;
  final DateTime fechaPago;
  final bool pagado;

  PagoGetDTO({
    required this.id,
    required this.cliente,
    required this.monto,
    required this.fechaPago,
    required this.pagado,
  });

  // Factory para crear una instancia desde JSON
  factory PagoGetDTO.fromJson(Map<String, dynamic> json) {
    return PagoGetDTO(
      id: json['id'],
      cliente: ClienteGetDTO.fromJson(json['cliente']),
      monto: json['monto']?.toDouble(),
      fechaPago: DateTime.parse(json['fechaPago']),
      pagado: json['pagado'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'cliente': cliente.toJson(),
      'monto': monto,
      'fechaPago': fechaPago.toIso8601String(),
      'pagado': pagado,
    };
  }
}
