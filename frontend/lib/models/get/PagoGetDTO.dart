import 'package:frontend/models/enums.dart';

class PagoGetDTO {
  final int? id;
  final int? clienteId;
  final String? clienteNombre;
  final List<int>? inscripcionesIds;
  final double? monto;
  final DateTime? fechaPago;
  final EstadoPago? estadoPago;
  final String? comentario;

  PagoGetDTO({
    this.id,
    this.clienteId,
    this.clienteNombre,
    this.inscripcionesIds,
    this.monto,
    this.fechaPago,
    this.estadoPago,
    this.comentario,
  });

  // Factory para crear una instancia desde JSON
  factory PagoGetDTO.fromJson(Map<String, dynamic> json) {
    return PagoGetDTO(
      id: json['id'],
      clienteId: json['clienteId'],
      clienteNombre: json['clienteNombre'],
      inscripcionesIds:
          json['inscripcionesIds'] != null
              ? List<int>.from(json['inscripcionesIds'])
              : null,
      monto: json['monto']?.toDouble(),
      fechaPago:
          json['fechaPago'] != null ? DateTime.parse(json['fechaPago']) : null,
      estadoPago: EstadoPago.values.firstWhere(
        (e) => e.toString() == 'EstadoPago.${json['estadoPago']}',
        orElse: () => EstadoPago.PENDIENTE,
      ),
      comentario: json['comentario'],
    );
  }

  // Método para convertir el objeto a JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clienteId': clienteId,
      'clienteNombre': clienteNombre,
      'inscripcionesIds': inscripcionesIds,
      'monto': monto,
      'fechaPago': fechaPago?.toIso8601String(),
      'estadoPago': estadoPago.toString().split('.').last,
      'comentario': comentario,
    };
  }
}
