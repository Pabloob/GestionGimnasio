import 'package:GymHub/pages/auth_pages/register.dart';
import 'package:flutter/material.dart';

import 'login.dart';

class WelcomePage extends StatelessWidget {
  const WelcomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                physics: const BouncingScrollPhysics(),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      // Logo de la aplicación
                      Hero(
                        tag: 'app_logo',
                        child: Image.asset(
                          "assets/images/logoImage.png",
                          width: MediaQuery.of(context).size.width * 0.5,
                          fit: BoxFit.contain,
                          errorBuilder: (context, error, stackTrace) {
                            return Icon(
                              Icons.sports_gymnastics,
                              size: MediaQuery.of(context).size.width * 0.5,
                              color: const Color(0xfffa6045),
                            );
                          },
                        ),
                      ),
                      const SizedBox(height: 60),
                      
                      // Título con gradiente
                      const _GradientText(
                        "Tu gimnasio,\n tu control",
                        style: TextStyle(
                          fontSize: 36,
                          fontWeight: FontWeight.bold,
                          height: 1.2,
                        ),
                        gradient: LinearGradient(
                          colors: [Color(0xfffa6045), Color(0xffff8960)],
                        ),
                      ),
                      const SizedBox(height: 24),
                      
                      // Descripción
                      const Text(
                        "Gestiona tu gimnasio de forma sencilla y eficiente. "
                        "Mantén un control organizado de usuarios, clases y pagos.",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 16,
                          color: Colors.black54,
                          fontWeight: FontWeight.w400,
                          height: 1.5,
                        ),
                      ),
                      const SizedBox(height: 60),
                    ],
                  ),
                ),
              ),
            ),
            
            // Botones de acción
            Padding(
              padding: const EdgeInsets.all(24.0),
              child: Column(
                children: [
                  // Botón de Iniciar sesión
                  SizedBox(
                    width: double.infinity,
                    height: 56,
                    child: ElevatedButton(
                      onPressed: () => _navigateTo(context, const LoginPage()),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xfffa6045),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        elevation: 2,
                      ),
                      child: const Text(
                        "Iniciar sesión",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w600,
                          color: Colors.white,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  
                  // Botón de Registrarse
                  SizedBox(
                    width: double.infinity,
                    height: 56,
                    child: OutlinedButton(
                      onPressed: () => _navigateTo(context, const RegisterPage()),
                      style: OutlinedButton.styleFrom(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        side: const BorderSide(color: Color(0xfffa6045), width: 2),
                      ),
                      child: const Text(
                        "Registrarse",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w600,
                          color: Color(0xfffa6045),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  void _navigateTo(BuildContext context, Widget page) {
    try {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => page,
        ),
      );
    } catch (e) {
      // Handle navigation error, e.g., show a snackbar or log the error
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Navigation error: $e')),
      );
    }
  }
}

class _GradientText extends StatelessWidget {
  final String text;
  final TextStyle? style;
  final Gradient gradient;

  const _GradientText(
    this.text, {
    required this.gradient,
    this.style,
  });

  @override
  Widget build(BuildContext context) {
    return ShaderMask(
      blendMode: BlendMode.srcIn,
      shaderCallback: (bounds) => gradient.createShader(
        Rect.fromLTWH(0, 0, bounds.width, bounds.height),
      ),
      child: Text(
        text,
        textAlign: TextAlign.center,
        style: style?.copyWith(
          color: Colors.white,
          height: style?.height,
        ),
      ),
    );
  }
}