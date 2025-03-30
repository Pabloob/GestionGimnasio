import 'package:frontend/models/enums.dart';

class PagoPutDTO {
  final int id;
  final int? clienteId;
  final double? monto;
  final DateTime? fechaPago;
  final EstadoPago? estadoPago;
  final String? comentario;
  final List<int>? inscripcionesIds;

  PagoPutDTO({
    required this.id,
    this.clienteId,
    this.monto,
    this.fechaPago,
    this.estadoPago,
    this.comentario,
    this.inscripcionesIds,
  });

  // Factory para crear una instancia desde JSON
  factory PagoPutDTO.fromJson(Map<String, dynamic> json) {
    return PagoPutDTO(
      id: json['id'],
      clienteId: json['clienteId'],
      monto: json['monto']?.toDouble(),
      fechaPago:
          json['fechaPago'] != null ? DateTime.parse(json['fechaPago']) : null,
      estadoPago: EstadoPago.values.firstWhere(
        (e) => e.toString() == 'EstadoPago.${json['estadoPago']}',
        orElse: () => EstadoPago.PENDIENTE,
      ),
      comentario: json['comentario'],
      inscripcionesIds:
          json['inscripcionesIds'] != null
              ? List<int>.from(json['inscripcionesIds'])
              : null,
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clienteId': clienteId,
      'monto': monto,
      'fechaPago': fechaPago?.toIso8601String(),
      'estadoPago': estadoPago.toString().split('.').last,
      'comentario': comentario,
      'inscripcionesIds': inscripcionesIds,
    };
  }
}
