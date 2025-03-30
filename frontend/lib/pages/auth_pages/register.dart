import 'package:flutter/material.dart';
import 'package:frontend/models/post/ClientePostDTO.dart';
import 'package:frontend/utils/common_widgets.dart';
import 'package:frontend/utils/date_picker.dart';
import 'package:intl/intl.dart';
import '../../apis/api_cliente.dart';
import '../../apis/api_service.dart';
import '../../utils/campo_contrasena.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nombreController = TextEditingController();
  final TextEditingController _contrasenaController = TextEditingController();
  final TextEditingController _correoController = TextEditingController();
  final TextEditingController _telefonoController = TextEditingController();
  final TextEditingController _fechaDeNacimientoController =
      TextEditingController();
  DateTime? _selectedDate;
  final ApiCliente _clienteService = ApiCliente(apiService: ApiService());
  String _mensajeError = "";
  bool _isLoading = false;

  @override
  void dispose() {
    _nombreController.dispose();
    _contrasenaController.dispose();
    _correoController.dispose();
    _telefonoController.dispose();
    _fechaDeNacimientoController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      bottomNavigationBar: _buildBottomBar(),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  _buildHeader(),
                  const SizedBox(height: 40),
                  _buildWelcomeText(),
                  const SizedBox(height: 30),
                  _buildNameField(),
                  const SizedBox(height: 20),
                  _buildPasswordField(),
                  const SizedBox(height: 20),
                  _buildEmailField(),
                  const SizedBox(height: 20),
                  _buildPhoneField(),
                  const SizedBox(height: 20),
                  _buildBirthDateField(),
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
      ),
    );
  }

  Widget _buildBottomBar() {
    return CommonWidgets.buildAuthBottomBar(
      textButton1: "Registrar",
      textButton2: "Cancelar",
      onClick1: _registrar,
      onClick2: () => Navigator.pop(context),
    );
  }

  Widget _buildHeader() {
    return Row(
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
    );
  }

  Widget _buildWelcomeText() {
    return const Text(
      "Encantado de verte",
      style: TextStyle(
        fontSize: 18,
        color: Colors.black54,
        fontWeight: FontWeight.w400,
      ),
    );
  }

  Widget _buildNameField() {
    return CommonWidgets.buildTextField(
      controller: _nombreController,
      label: "Nombre completo",
      icon: Icons.person,
      keyboardType: TextInputType.name,
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Por favor ingrese su nombre';
        }
        if (value.length < 3) {
          return 'El nombre debe tener al menos 3 caracteres';
        }
        return null;
      },
    );
  }

  Widget _buildPasswordField() {
    return PasswordFieldCustom(
      text: "Contraseña",
      controller: _contrasenaController,
    );
  }

  Widget _buildEmailField() {
    return CommonWidgets.buildTextField(
      controller: _correoController,
      label: "Correo electrónico",
      icon: Icons.email,
      keyboardType: TextInputType.emailAddress,
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Por favor ingrese su correo';
        }
        if (!RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$').hasMatch(value)) {
          return 'Ingrese un correo válido';
        }
        return null;
      },
    );
  }

  Widget _buildPhoneField() {
    return CommonWidgets.buildTextField(
      controller: _telefonoController,
      label: "Teléfono",
      icon: Icons.phone,
      keyboardType: TextInputType.phone,
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Por favor ingrese su teléfono';
        }
        if (!RegExp(r'^[0-9]{9,15}$').hasMatch(value)) {
          return 'Ingrese un teléfono válido';
        }
        return null;
      },
    );
  }

  Widget _buildBirthDateField() {
    return DatePickerWidget(
      controller: _fechaDeNacimientoController,
      label: "Fecha de nacimiento",
      onDateSelected: (selectedDate) {
        setState(() {
          _selectedDate = selectedDate;
          _fechaDeNacimientoController.text =
              "${_selectedDate!.day}/${_selectedDate!.month}/${_selectedDate!.year}";
        });
      },
    );
  }

  Future<void> _registrar() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _isLoading = true;
      _mensajeError = "";
    });

    try {
      final fechaDeNacimiento = DateFormat(
        'dd/MM/yyyy',
      ).parse(_fechaDeNacimientoController.text);

      final cliente = ClientePostDTO(
        nombre: _nombreController.text.trim(),
        contrasena: _contrasenaController.text,
        correo: _correoController.text.trim(),
        telefono: _telefonoController.text.trim(),
        fechaNacimiento: fechaDeNacimiento,
        activo: true,
      );

      await _clienteService.crearCliente(cliente);

      if (mounted) {
        _mostrarMensajeExito('Registro exitoso');
        Navigator.pop(context); // Regresar después de registro exitoso
      }
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
    if (error.toString().contains("409")) {
      return "El correo electrónico ya está registrado";
    }
    return "Error al registrar. Intente nuevamente";
  }

  void _mostrarMensajeExito(String mensaje) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(mensaje),
        backgroundColor: Colors.green,
        behavior: SnackBarBehavior.floating,
      ),
    );
  }
}
