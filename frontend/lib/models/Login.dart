enum TipoUsuario { CLIENTE, TRABAJADOR }

enum TipoTrabajador { ADMIN, RECEPCIONISTA, INSTRUCTOR }

class Login {
  final String correo;
  final String contrasena;

  Login({required this.correo, required this.contrasena});

  factory Login.fromJson(Map<String, dynamic> json) {
    return Login(correo: json['correo'], contrasena: json['contrasena']);
  }

  Map<String, dynamic> toJson() {
    return {'correo': correo, 'contrasena': contrasena};
  }
}
