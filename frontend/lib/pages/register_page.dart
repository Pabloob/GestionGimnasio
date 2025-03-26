import 'package:flutter/material.dart';
import 'package:frontend/models/Cliente.dart';
import 'package:intl/intl.dart';
import '../apis/api_cliente.dart';
import '../apis/api_service.dart';
import '../components/barra_inferior_botones.dart';
import '../components/campo_texto.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final TextEditingController _nombreController = TextEditingController();
  final TextEditingController _contrasenaController = TextEditingController();
  final TextEditingController _correoController = TextEditingController();
  final TextEditingController _telefonoController = TextEditingController();
  final TextEditingController _fechaDeNacimientoController =
      TextEditingController();

  // Instancia del ClienteService
  final ApiCliente clienteService = ApiCliente(apiService: ApiService());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      bottomNavigationBar: BottomNavigationBarCustom(
        textButton1: "Registrar",
        textButton2: "Cancelar",
        onClick1: () {
          _registrar(); // Llamar a la función de registro al hacer clic en "Registrar"
        },
        onClick2: () {
          Navigator.pop(context); // Volver atrás
        },
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    Image.asset("assets/icons/icon.png", height: 60),
                    const SizedBox(width: 20),
                    const Text(
                      "Registrar",
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                        color: Color(0xfffa6045),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 40),
                const Text(
                  "Encantado de verte",
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black54,
                    fontWeight: FontWeight.w400,
                  ),
                ),
                const SizedBox(height: 30),

                // Campos de texto
                TextfieldCustom(
                  controller: _nombreController,
                  text: "Nombre de usuario",
                  icono: Icon(Icons.person),
                  inputType: TextInputType.text,
                ),
                const SizedBox(height: 20),

                TextfieldCustom(
                  controller: _contrasenaController,
                  text: "Contraseña",
                  icono: Icon(Icons.lock),
                  inputType: TextInputType.text,
                  isPassword: true,
                ),
                const SizedBox(height: 20),

                TextfieldCustom(
                  controller: _correoController,
                  text: "Correo electrónico",
                  icono: Icon(Icons.email),
                  inputType: TextInputType.emailAddress,
                ),
                const SizedBox(height: 20),

                TextfieldCustom(
                  controller: _telefonoController,
                  text: "Teléfono",
                  icono: Icon(Icons.phone),
                  inputType: TextInputType.phone,
                ),
                const SizedBox(height: 20),

                TextfieldCustom(
                  controller: _fechaDeNacimientoController,
                  text: "Fecha de nacimiento",
                  icono: Icon(Icons.calendar_month),
                  inputType: TextInputType.datetime,
                ),
                const SizedBox(height: 20),
              ],
            ),
          ),
        ),
      ),
    );
  }

  // Función para registrar el cliente
  Future<void> _registrar() async {
    // Obtener los valores de los campos
    String nombre = _nombreController.text;
    String contrasena = _contrasenaController.text;
    String correo = _correoController.text;
    String telefono = _telefonoController.text;

    // Validar que los campos no estén vacíos
    if (nombre.isEmpty ||
        contrasena.isEmpty ||
        correo.isEmpty ||
        telefono.isEmpty) {
      _mostrarMensaje('Todos los campos son obligatorios');
      return;
    }

    // Obtener la fecha del campo de texto
    String fechaText = _fechaDeNacimientoController.text;

    // Convertir la fecha al formato 'yyyy-MM-dd'
    DateTime fechaDeNacimiento;
    try {
      fechaDeNacimiento = DateFormat(
        'dd/MM/yyyy',
      ).parse(fechaText); // El formato esperado es 'dd/MM/yyyy'
    } catch (e) {
      _mostrarMensaje('Formato de fecha inválido');
      return;
    }

    // Llamar a la función de anadirCliente
    try {
      await clienteService.crearCliente(
        Cliente(
          nombre: nombre,
          contrasena: contrasena,
          correo: correo,
          telefono: telefono,
          fechaDeNacimiento: fechaDeNacimiento,
          inasistencias: 0,
        ),
      );

      // Maneja la respuesta del servidor (por ejemplo, mostrar un mensaje de éxito o error)
      _mostrarMensaje('Cliente registrado con éxito');
    } catch (e) {
      _mostrarMensaje('Error al registrar cliente');
      print(e);
    }
  }

  // Función para mostrar mensajes
  void _mostrarMensaje(String mensaje) {
    ScaffoldMessenger.of(
      context,
    ).showSnackBar(SnackBar(content: Text(mensaje)));
  }
}
