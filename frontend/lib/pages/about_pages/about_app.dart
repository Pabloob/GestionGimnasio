import 'package:flutter/material.dart';

class AboutAppPage extends StatelessWidget {
  const AboutAppPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("About App", style: TextStyle(color: Colors.white)),
        backgroundColor: Color(0xFF0057FF),
        elevation: 0,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "Sobre esta aplicación",
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
            ),
            SizedBox(height: 20),
            // Información general de la app
            Text(
              "Esta aplicación está diseñada para ayudarte a gestionar tu experiencia en el gimnasio de manera eficiente. Podrás ver clases disponibles, realizar inscripciones, consultar tu perfil, y mucho más.",
              style: TextStyle(fontSize: 16, color: Colors.black54),
            ),
            SizedBox(height: 20),
            // Información sobre el equipo
            Text(
              "Desarrollado por: Pablo Orbea Benitez",
              style: TextStyle(fontSize: 16, color: Colors.black54),
            ),
            SizedBox(height: 20),
            // Versión de la app
            Text(
              "Versión: 1.0.0",
              style: TextStyle(fontSize: 16, color: Colors.black54),
            ),
            SizedBox(height: 20),
            // Redes sociales o contacto
            GestureDetector(
              onTap: () {
                // Lógica para abrir redes sociales o página web
              },
              child: Row(
                children: [
                  Icon(Icons.link, color: Color(0xFF0057FF)),
                  SizedBox(width: 10),
                  Text(
                    "Síguenos en nuestras redes sociales",
                    style: TextStyle(
                      fontSize: 16,
                      color: Color(0xFF0057FF),
                      fontWeight: FontWeight.bold,
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
}
