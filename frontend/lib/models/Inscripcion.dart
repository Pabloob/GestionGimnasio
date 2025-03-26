class Inscripcion {
  final int idCliente;
  final int idClase;
  final int? idPago; // Nullable ya que puede no tener pago inicialmente
  final DateTime fechaRegistro;
  final bool estadoPago;

  Inscripcion({
    required this.idCliente,
    required this.idClase,
    this.idPago,
    DateTime? fechaRegistro,
    required this.estadoPago,
  }) : fechaRegistro = fechaRegistro ?? DateTime.now(); // Fecha actual por defecto

  factory Inscripcion.fromJson(Map<String, dynamic> json) {
    return Inscripcion(
      idCliente: json['idCliente'],
      idClase: json['idClase'],
      idPago: json['idPago'],
      fechaRegistro: json['fechaRegistro'] != null 
          ? DateTime.parse(json['fechaRegistro']) 
          : null,
      estadoPago: json['estadoPago'] ?? false, // Por defecto false si no se especifica
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idCliente': idCliente,
      'idClase': idClase,
      'idPago': idPago,
      'fechaRegistro': fechaRegistro.toIso8601String(),
      'estadoPago': estadoPago,
    };
  }
}