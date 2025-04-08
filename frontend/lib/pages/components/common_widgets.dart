import 'package:flutter/material.dart';
import 'package:frontend/models/get/UserGetDTO.dart';
import 'package:frontend/theme/app_theme.dart';
import 'package:intl/intl.dart';

import '../../utils/Validator.dart';

enum ValidatorType { name, email, phone, password, number }

class CommonWidgets {
  // Widget para la barra inferior de navegación para las ventanas de login, registro y welcome
  static Widget buildAuthBottomBar({
    required String textButton1,
    required String textButton2,
    required VoidCallback onClick1,
    required VoidCallback onClick2,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 32, right: 24, left: 24),
      child: Row(
        children: [
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick1,
                style: ElevatedButton.styleFrom(
                  elevation: 2,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
                  ),
                  backgroundColor: AppTheme.primaryColor,
                ),
                child: Text(textButton1, style: AppTheme.buttonTextStyle),
              ),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick2,
                style: ElevatedButton.styleFrom(
                  elevation: 2,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
                  ),
                  backgroundColor: Colors.grey.shade300,
                ),
                child: Text(
                  textButton2,
                  style: AppTheme.buttonTextStyle.copyWith(
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

  // Widget para crear un campo de texto o contraseña
  static Widget buildTextField({
    required TextEditingController controller,
    required String label,
    required IconData icon,
    TextInputType? keyboardType,
    bool isPassword = false,
    ValidatorType? validatorType,
    required TextInputAction textInputAction,
    Future<void> Function(dynamic _)? onFieldSubmitted,
  }) {
    bool isPasswordVisible = false;

    return StatefulBuilder(
      builder: (context, setState) {
        return Padding(
          padding: const EdgeInsets.symmetric(vertical: 10),
          child: TextFormField(
            controller: controller,
            keyboardType:
                isPassword ? TextInputType.visiblePassword : keyboardType,
            obscureText: isPassword ? !isPasswordVisible : false,
            onFieldSubmitted: onFieldSubmitted,
            validator: (value) {
              if (validatorType != null) {
                switch (validatorType) {
                  case ValidatorType.name:
                    return Validator.validateName(value);
                  case ValidatorType.email:
                    return Validator.validateEmail(value);
                  case ValidatorType.phone:
                    return Validator.validatePhone(value);
                  case ValidatorType.password:
                    return Validator.validatePassword(value);
                  case ValidatorType.number:
                    return Validator.validateNumber(value);
                }
              }
              // Validación por defecto: campo obligatorio
              if (value == null || value.isEmpty) {
                return 'Este campo es requerido';
              }
              return null;
            },
            textInputAction: textInputAction,
            decoration: InputDecoration(
              labelText: label,
              prefixIcon: Icon(icon, color: const Color(0xfffa6045)),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(12),
              ),
              filled: true,
              fillColor: Colors.white,
              suffixIcon:
                  isPassword
                      ? IconButton(
                        icon: Icon(
                          isPasswordVisible
                              ? Icons.visibility
                              : Icons.visibility_off,
                          color: const Color(0xfffa6045),
                        ),
                        onPressed: () {
                          setState(() {
                            isPasswordVisible = !isPasswordVisible;
                          });
                        },
                      )
                      : null,
              errorStyle: const TextStyle(fontSize: 12),
            ),
          ),
        );
      },
    );
  }

  // Widget para crear un campo de texto para seleccionar la hora
  static Widget buildTimeField({
    required BuildContext context,
    required TextEditingController controller,
    required String label,
    TextInputAction textInputAction = TextInputAction.next,
    Future<void> Function(String)? onFieldSubmitted,
  }) {
    return TextFormField(
      controller: controller,
      decoration: AppTheme.inputDecoration(label).copyWith(
        prefixIcon: Icon(Icons.access_time, color: AppTheme.primaryColor),
      ),
      readOnly: true,
      textInputAction: textInputAction,
      onTap: () => _selectTime(context, controller),
      onFieldSubmitted: onFieldSubmitted,
      validator: (value) {
        if (value == null || value.isEmpty) {
          return "Este campo es obligatorio";
        }
        try {
          DateFormat("HH:mm").parse(value);
          return null;
        } catch (e) {
          return "Formato inválido (HH:mm)";
        }
      },
    );
  }

  // Función para seleccionar hora
  static Future<void> _selectTime(
    BuildContext context,
    TextEditingController controller,
  ) async {
    final time = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
      builder: (BuildContext context, Widget? child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: ColorScheme.light(primary: AppTheme.primaryColor),
            timePickerTheme: TimePickerThemeData(
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
              ),
            ),
          ),
          child: child!,
        );
      },
    );

    if (time != null) {
      controller.text =
          "${time.hour.toString().padLeft(2, '0')}:${time.minute.toString().padLeft(2, '0')}";
    }
  }

  // Widget para crear un mensaje de encabezado personalizado
  static Widget buildCustomTopMesage({
    UserGetDTO? user,
    String? textoPrincipal,
    String? textoSecundario,
    CircleAvatar? avatar,
  }) {
    return Container(
      padding: AppTheme.defaultPadding,
      decoration: BoxDecoration(
        color: AppTheme.primaryColor,
        borderRadius: const BorderRadius.only(
          bottomLeft: Radius.circular(20),
          bottomRight: Radius.circular(20),
        ),
      ),
      width: double.infinity,
      child: Row(
        children: [
          if (avatar != null) ...[avatar, const SizedBox(width: 16)],
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                '${textoPrincipal ?? ''} ${user?.name ?? ''}',
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              if (textoSecundario != null)
                Text(
                  textoSecundario,
                  style: const TextStyle(color: Colors.white70, fontSize: 14),
                ),
            ],
          ),
        ],
      ),
    );
  }

}
