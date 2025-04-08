import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/pages/components/common_widgets.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/theme/app_theme.dart';
import 'package:frontend/utils/utils.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/UserLoginDTO.dart';
import '../../utils/authService.dart';

class LoginPage extends ConsumerStatefulWidget {
  const LoginPage({super.key});

  @override
  ConsumerState<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends ConsumerState<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _rememberMe = false;
  bool _isLoading = false;
  String _errorMessage = '';

  @override
  void initState() {
    super.initState();
    _cargarCredencialesGuardadas();
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _cargarCredencialesGuardadas() async {
    final prefs = await SharedPreferences.getInstance();
    final correoGuardado = prefs.getString('correo_guardado');

    if (correoGuardado != null && correoGuardado.isNotEmpty) {
      setState(() {
        _emailController.text = correoGuardado;
        _rememberMe = true;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => FocusScope.of(context).unfocus(),
      child: Scaffold(
        backgroundColor: AppTheme.backgroundColor,
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
                        Icons.fitness_center,
                        size: 40,
                        color: AppTheme.secondaryColor,
                      ),
                      const SizedBox(width: 16),
                      Text(
                        "Iniciar sesión",
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
                    "Bienvenido de nuevo",
                    style: Theme.of(
                      context,
                    ).textTheme.bodyLarge?.copyWith(color: Colors.black54),
                  ),
                  const SizedBox(height: 40),
                  // Campos de formulario
                  _buildEmailField(),
                  const SizedBox(height: 20),
                  _buildPasswordField(),
                  const SizedBox(height: 16),
                  _buildRememberMeRow(),
                  const SizedBox(height: 24),
                  if (_errorMessage.isNotEmpty) _buildErrorContainer(),
                  const SizedBox(height: 24),
                  _buildLoginButton(),
                ],
              ),
            ),
          ),
        ),
      ),
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
      label: "Contraseña",
      icon: Icons.lock,
      isPassword: true,
      textInputAction: TextInputAction.done,
      validatorType: ValidatorType.password,
      onFieldSubmitted: (_) => _handleLogin(),
    );
  }

  Widget _buildRememberMeRow() {
    return Row(
      children: [
        Checkbox(
          value: _rememberMe,
          onChanged: (value) => setState(() => _rememberMe = value ?? false),
          activeColor: const Color(0xfffa6045),
        ),
        const Text("Recordar mis datos"),
        const Spacer(),
        TextButton(
          onPressed: _showForgotPasswordDialog,
          child: const Text(
            "¿Olvidaste tu contraseña?",
            style: TextStyle(color: Color(0xfffa6045)),
          ),
        ),
      ],
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

  Widget _buildLoginButton() {
    return SizedBox(
      width: double.infinity,
      child: ElevatedButton(
        onPressed: _isLoading ? null : _handleLogin,
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
                  "Iniciar sesión",
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w600,
                    color: Colors.white,
                  ),
                ),
      ),
    );
  }

  Future<void> _handleLogin() async {
    if (!_formKey.currentState!.validate()) return;

    FocusScope.of(context).unfocus();
    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    try {
      final response = await ref
          .read(usuarioServiceProvider)
          .login(
            UserLoginDTO(
              email: _emailController.text.trim(),
              password: _passwordController.text.trim(),
            ),
          );

      if (_rememberMe) {
        final prefs = await SharedPreferences.getInstance();
        await prefs.setString('correo_guardado', _emailController.text.trim());
      } else {
        final prefs = await SharedPreferences.getInstance();
        await prefs.remove('correo_guardado');
      }

      await _processLoginResponse(response);
    } catch (e) {
      setState(() {
        _errorMessage = getErrorMessage(e);
      });
    } finally {
      if (mounted) {
        setState(() => _isLoading = false);
      }
    }
  }

  Future<void> _processLoginResponse(Map<String, dynamic> response) async {
    final token = response['token'];
    final role = response['role'];
    AuthService authService = AuthService();
    final user = authService.extraerUsuario(response, role);

    if (user == null) throw Exception('Error al obtener datos del usuario');

    // Guardar sesión y navegar
    await authService.saveSession(token, user);
    if (mounted) await authService.navegarSegunRol(role, context);
  }

  void _showForgotPasswordDialog() {
    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            title: const Text("Recuperar contraseña"),
            content: const Text(
              "Ingresa tu correo para restablecer tu contraseña",
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: const Text("Cancelar"),
              ),
              TextButton(
                onPressed: () {
                  Navigator.pop(context);
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text("Instrucciones enviadas a tu correo"),
                    ),
                  );
                },
                child: const Text("Enviar"),
              ),
            ],
          ),
    );
  }
}
