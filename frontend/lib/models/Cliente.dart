enum TipoUsuario { CLIENTE, TRABAJADOR }

class Cliente {
  final String nombre;
  final String contrasena;
  final String correo;
  final String telefono;
  final DateTime fechaDeNacimiento;
  final DateTime fechaRegistro;
  final TipoUsuario tipoUsuario;
  final bool activo;

  final int inasistencias;

  Cliente({
    required this.nombre,
    required this.contrasena,
    required this.correo,
    required this.telefono,
    required this.fechaDeNacimiento,
    required this.inasistencias,
    DateTime? fechaRegistro,
    TipoUsuario? tipoUsuario,
    bool? activo,
  }) : fechaRegistro = fechaRegistro ?? DateTime.now(),
       tipoUsuario = tipoUsuario ?? TipoUsuario.CLIENTE,
       activo = activo ?? true;

  factory Cliente.fromJson(Map<String, dynamic> json) {
    return Cliente(
      nombre: json['nombre'],
      contrasena: json['contrasena'],
      correo: json['correo'],
      telefono: json['telefono'],
      fechaDeNacimiento: DateTime.parse(json['fechaDeNacimiento']),
      inasistencias: json['inasistencias'],
      fechaRegistro:
          json['fechaRegistro'] != null
              ? DateTime.parse(json['fechaRegistro'])
              : null,
      tipoUsuario: TipoUsuario.values.firstWhere(
            (e) => e.name == json["tipoUsuario"],
      ),
      activo: json['activo'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'contrasena': contrasena,
      'correo': correo,
      'telefono': telefono,
      'fechaDeNacimiento': fechaDeNacimiento.toIso8601String(),
      'inasistencias': inasistencias,
      'fechaRegistro': fechaRegistro.toIso8601String(),
      'tipoUsuario': tipoUsuario.name,
      'activo': activo,
    };
  }
}
