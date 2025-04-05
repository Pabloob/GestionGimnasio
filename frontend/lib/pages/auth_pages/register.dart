import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/post/ClientePostDTO.dart';
import 'package:frontend/providers/cliente_providers.dart';

import '../../models/enums.dart';
import '../../models/post/UsuarioPostDTO.dart';
import '../../theme/app_theme.dart';
import '../components/common_widgets.dart';
import '../components/date_picker.dart';

class RegisterPage extends ConsumerStatefulWidget {
  const RegisterPage({super.key});

  @override
  ConsumerState<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends ConsumerState<RegisterPage> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _phoneController = TextEditingController();
  final _birthDateController = TextEditingController();

  DateTime? _birthDate;
  bool _isLoading = false;
  String _errorMessage = '';

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    _phoneController.dispose();
    _birthDateController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: SingleChildScrollView(
          physics: const BouncingScrollPhysics(),
          padding: const EdgeInsets.symmetric(horizontal: 24),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 40),
                // Título con icono
                Row(
                  children: [
                    Icon(
                      Icons.person_add,
                      size: 40,
                      color: AppTheme.secondaryColor,
                    ),
                    const SizedBox(width: 16),
                    Text(
                      "Crear cuenta",
                      style: Theme.of(
                        context,
                      ).textTheme.headlineMedium?.copyWith(
                        fontWeight: FontWeight.bold,
                        color: AppTheme.secondaryColor,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 8),
                Text(
                  "Completa tus datos para registrarte",
                  style: Theme.of(
                    context,
                  ).textTheme.bodyLarge?.copyWith(color: Colors.black54),
                ),
                const SizedBox(height: 40),
                // Campos del formulario
                _buildNameField(),
                const SizedBox(height: 20),
                _buildEmailField(),
                const SizedBox(height: 20),
                _buildPasswordField(),
                const SizedBox(height: 20),
                _buildPhoneField(),
                const SizedBox(height: 20),
                _buildBirthDateField(),
                const SizedBox(height: 24),
                if (_errorMessage.isNotEmpty) _buildErrorContainer(),
                const SizedBox(height: 24),
                _buildRegisterButton(),
                const SizedBox(height: 24),
                _buildLoginInsteadButton(),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildNameField() {
    return CommonWidgets.buildTextField(
      controller: _nameController,
      keyboardType: TextInputType.name,
      label: "Nombre completo",
      icon: Icons.person,
      textInputAction: TextInputAction.next,
      validatorType: ValidatorType.name,
    );
  }

  Widget _buildEmailField() {
    return CommonWidgets.buildTextField(
      controller: _emailController,
      keyboardType: TextInputType.emailAddress,
      label: "Correo electrónico",
      icon: Icons.person,
      textInputAction: TextInputAction.next,
      validatorType: ValidatorType.email,
    );
  }

  Widget _buildPasswordField() {
    return CommonWidgets.buildTextField(
      controller: _passwordController,
      keyboardType: TextInputType.visiblePassword,
      label: "Contraseña",
      icon: Icons.lock,
      textInputAction: TextInputAction.next,
      validatorType: ValidatorType.password,
      isPassword: true,
    );
  }

  Widget _buildPhoneField() {
    return CommonWidgets.buildTextField(
      controller: _phoneController,
      keyboardType: TextInputType.phone,
      label: "Teléfono",
      icon: Icons.phone,
      textInputAction: TextInputAction.next,
      validatorType: ValidatorType.phone,
    );
  }

  Widget _buildBirthDateField() {
    return DatePickerWidget(
      controller: _birthDateController,
      label: "Fecha de nacimiento",
      onDateSelected: (selectedDate) {
        setState(() {
          _birthDate = selectedDate;
          _birthDateController.text =
          "${_birthDate!.day}/${_birthDate!.month}/${_birthDate!.year}";
        });
      },
    );
  }

  Widget _buildErrorContainer() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.red[50],
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        children: [
          const Icon(Icons.error_outline, color: Colors.red),
          const SizedBox(width: 8),
          Expanded(
            child: Text(
              _errorMessage,
              style: const TextStyle(color: Colors.red),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildRegisterButton() {
    return SizedBox(
      width: double.infinity,
      child: ElevatedButton(
        onPressed: _isLoading ? null : _handleRegistration,
        style: ElevatedButton.styleFrom(
          backgroundColor: const Color(0xfffa6045),
          padding: const EdgeInsets.symmetric(vertical: 16),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
        ),
        child:
            _isLoading
                ? const CircularProgressIndicator(color: Colors.white)
                : const Text(
                  "Registrarse",
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w600,
                    color: Colors.white,
                  ),
                ),
      ),
    );
  }

  Widget _buildLoginInsteadButton() {
    return Center(
      child: TextButton(
        onPressed: () => Navigator.pop(context),
        child: const Text(
          "¿Ya tienes una cuenta? Inicia sesión",
          style: TextStyle(color: Color(0xfffa6045)),
        ),
      ),
    );
  }

  Future<void> _handleRegistration() async {
    if (!_formKey.currentState!.validate()) return;
    if (_birthDate == null) {
      setState(
        () => _errorMessage = 'Por favor selecciona tu fecha de nacimiento',
      );
      return;
    }

    FocusScope.of(context).unfocus();
    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    try {
      final cliente = ClientePostDTO(
        usuarioPostDTO: UsuarioPostDTO(
          nombre: _nameController.text.trim(),
          correo: _emailController.text.trim(),
          contrasena: _passwordController.text.trim(),
          telefono: _phoneController.text.trim(),
          fechaNacimiento: _birthDate!,
          tipoUsuario: TipoUsuario.CLIENTE,
          activo: true,
        ),
      );

      await ref.read(registerClienteProvider(cliente).future);

      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Registro exitoso'),
            backgroundColor: Colors.green,
          ),
        );
        Navigator.pop(context);
      }
    } catch (e) {
      setState(() => _errorMessage = _getErrorMessage(e));
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  String _getErrorMessage(dynamic error) {
    if (error.toString().contains('socket') ||
        error.toString().contains('connection')) {
      return 'Error de conexión. Verifica tu internet';
    } else if (error.toString().contains('409')) {
      return 'El correo electrónico ya está registrado';
    }
    return 'Error al registrar. Intenta nuevamente';
  }
}
