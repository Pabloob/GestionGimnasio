import 'package:flutter/material.dart';

class TextfieldCustom extends StatefulWidget {
  final String text;
  final Icon icono;
  final TextInputType inputType;
  final bool isPassword;
  final TextEditingController? controller;

  const TextfieldCustom({
    super.key,
    required this.text,
    required this.icono,
    this.inputType = TextInputType.text,
    this.isPassword = false,
    this.controller,
  });

  @override
  State<TextfieldCustom> createState() => _TextfieldCustomState();
}

class _TextfieldCustomState extends State<TextfieldCustom> {
  bool _isPasswordVisible = false;

  @override
  Widget build(BuildContext context) {
    final controller = widget.controller;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: Text(
            widget.text,
            style: const TextStyle(
              fontSize: 13,
              color: Colors.black54,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
        const SizedBox(height: 5),
        TextField(
          controller: controller,
          keyboardType: widget.inputType,
          obscureText: widget.isPassword && !_isPasswordVisible,
          readOnly: widget.inputType == TextInputType.datetime,
          decoration: InputDecoration(
            prefixIcon: widget.icono,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10),
            ),
            suffixIcon: widget.isPassword
                ? IconButton(
              icon: Icon(_isPasswordVisible
                  ? Icons.visibility
                  : Icons.visibility_off),
              onPressed: () {
                setState(() {
                  _isPasswordVisible = !_isPasswordVisible;
                });
              },
            )
                : null,
          ),
          onTap: () async {
            if (widget.inputType == TextInputType.datetime) {
              DateTime? pickedDate = await showDatePicker(
                context: context,
                initialDate: DateTime.now(),
                firstDate: DateTime(1900),
                lastDate: DateTime.now(),
              );
              if (pickedDate != null) {
                setState(() {
                  controller?.text =
                  "${pickedDate.day}/${pickedDate.month}/${pickedDate.year}";
                });
              }
            }
          },
        ),
      ],
    );
  }
}
