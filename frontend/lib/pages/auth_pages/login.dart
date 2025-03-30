import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/models/Login.dart';
import 'package:frontend/models/get/ClienteGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/pages/client_pages/client_main.dart';
import 'package:frontend/pages/instructor_pages/instructor_home_page.dart';
import 'package:frontend/utils/campo_contrasena.dart';
import 'package:frontend/utils/common_widgets.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../apis/api_service.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _correoController = TextEditingController();
  final TextEditingController _contrasenaController = TextEditingController();
  final ApiUsuario _apiUsuario = ApiUsuario(apiService: ApiService());

  String _mensajeError = "";
  bool _isLoading = false;

  @override
  void dispose() {
    _correoController.dispose();
    _contrasenaController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      bottomNavigationBar: _buildBottomBar(),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _buildHeader(),
                const SizedBox(height: 60),
                _buildWelcomeText(),
                const SizedBox(height: 30),

                CommonWidgets.buildTextField(
                  controller: _correoController,
                  label: "Correo electrónico",
                  icon: Icons.email,
                  keyboardType: TextInputType.emailAddress,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor ingrese su correo';
                    }
                    if (!RegExp(
                      r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$',
                    ).hasMatch(value)) {
                      return 'Ingrese un correo válido';
                    }
                    return null;
                  },
                ),

                const SizedBox(height: 20),

                PasswordFieldCustom(
                  text: "Contraseña",
                  controller: _contrasenaController,
                ),

                const SizedBox(height: 20),
                CommonWidgets.buildErrorText(
                  text: _mensajeError,
                  isVisible: !_isLoading,
                ),
                if (_isLoading)
                  const Center(child: CircularProgressIndicator()),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildBottomBar() {
    return CommonWidgets.buildAuthBottomBar(
      textButton1: "Iniciar sesión",
      textButton2: "Cancelar",
      onClick1: _autenticar,
      onClick2: () => Navigator.pop(context),
    );
  }

  Widget _buildHeader() {
    return Padding(
      padding: const EdgeInsets.only(top: 50),
      child: Row(
        children: [
          Image.asset("assets/icons/icon.png", height: 60),
          const SizedBox(width: 20),
          const Text(
            "Iniciar sesión",
            style: TextStyle(
              fontSize: 32,
              fontWeight: FontWeight.bold,
              color: Color(0xfffa6045),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildWelcomeText() {
    return const Text(
      "Encantado de verte de nuevo",
      style: TextStyle(
        fontSize: 18,
        color: Colors.black54,
        fontWeight: FontWeight.w400,
      ),
    );
  }

  Future<void> _autenticar() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _isLoading = true;
      _mensajeError = "";
    });

    try {
      final loginData = UsuarioLoginDTO(
        correo: _correoController.text.trim(),
        contrasena: _contrasenaController.text,
      );

      final respuesta = await _apiUsuario.login(loginData);
      final token = respuesta['token'];
      final usuario = _extraerUsuario(respuesta);

      if (usuario == null) {
        throw Exception("Error al obtener datos del usuario");
      }

      await _guardarDatosSesion(token, usuario);

      final role = respuesta['role']; 
      await _navegarSegunRol(usuario, role);
    } catch (e) {
      setState(() {
        _mensajeError = _getErrorMessage(e);
      });
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  String _getErrorMessage(dynamic error) {
    if (error.toString().contains("socket") ||
        error.toString().contains("connection")) {
      return "Error de conexión. Verifique su internet";
    }
    return "Credenciales incorrectas o servicio no disponible";
  }

  Future<void> _guardarDatosSesion(String token, dynamic usuario) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('token', token);

    Map<String, dynamic> usuarioMap;
    if (usuario is ClienteGetDTO) {
      usuarioMap = usuario.toJson();
      usuarioMap['tipoUsuario'] = 'CLIENTE';
    } else if (usuario is TrabajadorGetDTO) {
      usuarioMap = usuario.toJson();
      usuarioMap['tipoUsuario'] = 'TRABAJADOR';
    } else {
      throw Exception("Tipo de usuario no reconocido");
    }

    final usuarioJson = jsonEncode(usuarioMap);

    await prefs.setString('usuario', usuarioJson);
  }

  dynamic _extraerUsuario(Map<String, dynamic> respuesta) {
    if (respuesta.containsKey('userDetails')) {
      final userDetails = respuesta['userDetails'];
      final role = respuesta['role'];

      if (role == 'CLIENTE') {
        return ClienteGetDTO.fromJson(userDetails);
      } else if (role == 'ENTRENADOR' || role == 'RECEPCIONISTA') {
        return TrabajadorGetDTO.fromJson(userDetails);
      }
    }
    return null;
  }

  Future<void> _navegarSegunRol(dynamic usuario, String role) async {
    Widget paginaDestino;

    switch (role) {
      case "CLIENTE":
        paginaDestino = const ClientMainScreen();
        break;
      case "ENTRENADOR":
      case "RECEPCIONISTA":
        paginaDestino = const InstructorHomePage();
        break;
      default:
        throw Exception("Rol de usuario no reconocido");
    }

    if (!mounted) return;

    await Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => paginaDestino),
      (Route<dynamic> route) => false,
    );
  }
}
