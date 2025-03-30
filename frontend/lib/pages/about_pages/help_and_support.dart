import 'package:flutter/material.dart';

class HelpAndSupportPage extends StatelessWidget {
  const HelpAndSupportPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Help & Support", style: TextStyle(color: Colors.white)),
        backgroundColor: Color(0xFF0057FF),
        elevation: 0,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "¿Cómo podemos ayudarte?",
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
            ),
            SizedBox(height: 20),
            // Preguntas Frecuentes
            GestureDetector(
              onTap: () {
                // Lógica para abrir FAQ o página web
              },
              child: Card(
                color: Color(0xFFF5F5F5),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Row(
                    children: [
                      Icon(Icons.question_answer, color: Color(0xFF0057FF)),
                      SizedBox(width: 10),
                      Text(
                        "Preguntas frecuentes",
                        style: TextStyle(
                          fontSize: 16,
                          color: Color(0xFF0057FF),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            SizedBox(height: 16),
            // Contactar con Soporte
            GestureDetector(
              onTap: () {
                // Lógica para contactar con soporte
              },
              child: Card(
                color: Color(0xFFF5F5F5),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Row(
                    children: [
                      Icon(Icons.email, color: Color(0xFF0057FF)),
                      SizedBox(width: 10),
                      Text(
                        "Contactar con soporte",
                        style: TextStyle(
                          fontSize: 16,
                          color: Color(0xFF0057FF),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
