import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/models/Cliente.dart';
import 'package:frontend/models/Login.dart';
import 'package:frontend/models/Trabajador.dart';
import 'package:frontend/pages/client_home_page.dart';
import 'package:frontend/pages/instructor_home_page.dart';
import 'package:frontend/utils/utils.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../apis/api_service.dart';
import '../components/barra_inferior_botones.dart';
import '../components/campo_texto.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _correoController = TextEditingController();
  final TextEditingController _contrasenaController = TextEditingController();

  // Instancia del ClienteService
  final ApiService apiService = ApiService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      bottomNavigationBar: BottomNavigationBarCustom(
        textButton1: "Iniciar sesion",
        textButton2: "Cancelar",
        onClick1: () {
          _autenticar();
        },
        onClick2: () {
          Navigator.pop(context);
        },
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 50),
                child: Row(
                  children: [
                    Image.asset("assets/icons/icon.png", height: 60),
                    const SizedBox(width: 20),
                    const Text(
                      "Iniciar sesion",
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                        color: Color(0xfffa6045),
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 60),

              // Texto de bienvenida
              const Text(
                "Encantado de verte de nuevo",
                style: TextStyle(
                  fontSize: 18,
                  color: Colors.black54,
                  fontWeight: FontWeight.w400,
                ),
              ),

              const SizedBox(height: 30),

              // Campo de Nombre de Usuario
              TextfieldCustom(
                controller: _correoController,
                text: "Correo electrónico",
                icono: Icon(Icons.email),
                inputType: TextInputType.emailAddress,
              ),

              const SizedBox(height: 20),

              // Campo de Contraseña con botón para mostrar/ocultar
              TextfieldCustom(
                controller: _contrasenaController,
                text: "Contraseña",
                icono: Icon(Icons.lock),
                inputType: TextInputType.text,
                isPassword: true,
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _autenticar() async {
    if (_correoController.text.isEmpty || _contrasenaController.text.isEmpty) {
      if (mounted) {
        mostrarMensaje('Por favor ingrese su correo y contraseña', context);
      }
      return;
    }

    final loginData = Login(
      correo: _correoController.text.trim(),
      contrasena: _contrasenaController.text,
    );

    final respuesta = await apiService.login(loginData);

    if (!respuesta.containsKey('token')) {
      if (mounted) {
        mostrarMensaje('Credenciales incorrectas', context);
      }
      return;
    }

    final token = respuesta['token'];
    dynamic usuario =
        respuesta.containsKey('cliente')
            ? Cliente.fromJson(respuesta['cliente'])
            : respuesta.containsKey('trabajador')
            ? Trabajador.fromJson(respuesta['trabajador'])
            : null;

    if (usuario == null) {
      if (mounted) {
        mostrarMensaje('Error al obtener datos del usuario', context);
      }
      return;
    }

    await _guardarDatosSesion(token, usuario);

    if (!mounted) return;

    final String? userRol = await obtenerTipoUsuarioIdDesdeToken();
    if (userRol == null) return;

    if (mounted) {
      // Only perform navigation if mounted
      if (userRol == "ROLE_CLIENTE") {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const ClientHomePage()),
        );
      } else if (userRol == "ROLE_INSTRUCTOR") {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const InstructorHomePage()),
        );
      }

      // Show the success message
      mostrarMensaje('Bienvenido ${usuario.nombre}', context);
    }
  }

  Future<void> _guardarDatosSesion(String token, dynamic usuario) async {
    final prefs = await SharedPreferences.getInstance();

    await prefs.setString('token', token);

    if (usuario != null) {
      final String usuarioJson = jsonEncode(usuario.toJson());
      await prefs.setString('usuario', usuarioJson);
    }
  }
}
