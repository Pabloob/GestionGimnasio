import 'package:flutter/material.dart';
import 'package:frontend/pages/register_page.dart';
import '../components/barra_inferior_botones.dart';
import 'login_page.dart';

class WelcomePage extends StatelessWidget {
  const WelcomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      bottomNavigationBar: BottomNavigationBarCustom(
        textButton1: "Iniciar sesion",
        textButton2: "Registrar",
        onClick1: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const LoginPage()),
          );
        },
        onClick2: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const RegisterPage()),
          );
        },
      ),
      body: SingleChildScrollView(
        child: LayoutBuilder(
          builder: (context, constraints) {
            return Padding(
              padding: const EdgeInsets.only(
                right: 24,
                left: 24,
                bottom: 40,
                top: 80,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  // Logo ajustado a pantallas
                  constraints.maxWidth > 600
                      ? Center(
                        child: Image.asset(
                          "assets/images/logoImage.png",
                          width: 150,
                        ),
                      )
                      : Center(
                        child: Image.asset(
                          "assets/images/logoImage.png",
                          width: 200,
                        ),
                      ),

                  const SizedBox(height: 100),
                  // Título principal
                  const Text(
                    "Tu gimnasio,\n tu control",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 36,
                      fontWeight: FontWeight.bold,
                      color: Color(0xfffa6045),
                    ),
                  ),
                  const SizedBox(height: 20),
                  // Descripción
                  const Text(
                    "Gestiona tu gimnasio de forma sencilla y eficiente. Mantén un control organizado de usuarios, clases y pagos.",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.black54,
                      fontWeight: FontWeight.w400,
                    ),
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}
