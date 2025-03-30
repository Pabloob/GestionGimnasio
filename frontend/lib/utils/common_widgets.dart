import 'package:flutter/material.dart';

class CommonWidgets {
  // Widget para la barra inferior de navegacion para las ventanas de login registro y welcome
  static Widget buildAuthBottomBar({
    required String textButton1,
    required String textButton2,
    required VoidCallback onClick1,
    required VoidCallback onClick2,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 88, right: 24, left: 24),
      child: Row(
        children: [
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick1,
                style: ElevatedButton.styleFrom(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  backgroundColor: const Color(0xfffa6045),
                  shadowColor: Colors.black26,
                ),
                child: Text(
                  textButton1,
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ),
          const SizedBox(width: 20),
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick2,
                style: ElevatedButton.styleFrom(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  backgroundColor: const Color(0XFF939393),
                  shadowColor: Colors.black26,
                ),
                child: Text(
                  textButton2,
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  // Widget para mostrar un mensaje de error
  static Widget buildErrorText({
    required String text,
    bool isVisible = true,
    EdgeInsets padding = const EdgeInsets.only(top: 8),
    TextStyle? style,
    TextAlign? textAlign,
  }) {
    return Visibility(
      visible: isVisible && text.isNotEmpty,
      child: Padding(
        padding: padding,
        child: Text(
          text,
          style:
              style ??
              const TextStyle(
                fontSize: 14,
                color: Colors.red,
                fontWeight: FontWeight.normal,
              ),
          textAlign: textAlign ?? TextAlign.center,
        ),
      ),
    );
  }

  // Widget para crear un campo de texto
  static Widget buildTextField({
    required TextEditingController controller,
    required String label,
    required IconData icon,
    TextInputType? keyboardType,
    bool obscureText = false,
    String? Function(String?)? validator,
  }) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10),
      child: TextFormField(
        controller: controller,
        keyboardType: keyboardType,
        obscureText: obscureText,
        validator: validator,
        decoration: InputDecoration(
          labelText: label,
          prefixIcon: Icon(icon, color: Color(0xfffa6045)),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
          filled: true,
          fillColor: Colors.white,
        ),
      ),
    );
  }

  // Widget para crear un campo de texto
  static Widget buildCustomTopMesage({
    required dynamic user,
    String? textoPrincipal,
    String? textoSecundario,
    CircleAvatar? avatar,
  }) {
    return Container(
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Color.fromARGB(255, 106, 156, 255), // Color principal
        borderRadius: BorderRadius.only(
          bottomLeft: Radius.circular(20),
          bottomRight: Radius.circular(20),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Row(
            children: [
              if (avatar != null) ...[avatar, SizedBox(width: 16)],
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '${textoPrincipal ?? ''} ${user?.nombre ?? 'Usuario'}',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  if (textoSecundario != null)
                    Text(
                      textoSecundario,
                      style: TextStyle(color: Colors.white, fontSize: 14),
                    ),
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}
