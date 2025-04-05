import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/models/post/TrabajadorPostDTO.dart';
import 'package:frontend/models/post/UsuarioPostDTO.dart';
import 'package:frontend/providers/trabajador_provider.dart';
import 'package:frontend/pages/components/date_picker.dart';
import 'package:intl/intl.dart';

import '../components/common_widgets.dart';

class AddTrabajadorForm extends ConsumerStatefulWidget {
  final VoidCallback? onEnrollmentSuccess;

  const AddTrabajadorForm({super.key, this.onEnrollmentSuccess});

  @override
  ConsumerState<AddTrabajadorForm> createState() => _AddTrabajadorFormState();
}

class _AddTrabajadorFormState extends ConsumerState<AddTrabajadorForm> {
  final TextEditingController _nombreController = TextEditingController();
  final TextEditingController _contrasenaController = TextEditingController();
  final TextEditingController _correoController = TextEditingController();
  final TextEditingController _telefonoController = TextEditingController();
  final TextEditingController _fechaNacimientoController =
      TextEditingController();
  final TextEditingController _direccionController = TextEditingController();
  final TextEditingController _horaInicioController = TextEditingController();
  final TextEditingController _horaFinController = TextEditingController();
  final _formKeyTrabajador = GlobalKey<FormState>();
  DateTime? _selectedDate;
  TipoTrabajador? _tipoTrabajador;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _nombreController.dispose();
    _correoController.dispose();
    _telefonoController.dispose();
    _fechaNacimientoController.dispose();
    _direccionController.dispose();
    _horaInicioController.dispose();
    _horaFinController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Form(
        key: _formKeyTrabajador,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CommonWidgets.buildTextField(
              controller: _nombreController,
              label: "Nombre completo",
              icon: Icons.person,
              textInputAction: TextInputAction.next,
              keyboardType: TextInputType.name,
              validatorType: ValidatorType.name,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _contrasenaController,
              label: "Contraseña",
              icon: Icons.lock,
              textInputAction: TextInputAction.next,
              keyboardType: TextInputType.visiblePassword,
              validatorType: ValidatorType.password,
              isPassword: true,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _correoController,
              label: "Correo electrónico",
              icon: Icons.email,
              keyboardType: TextInputType.emailAddress,
              validatorType: ValidatorType.email,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _telefonoController,
              label: "Teléfono",
              icon: Icons.phone,
              keyboardType: TextInputType.phone,
              validatorType: ValidatorType.phone,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 16),
            DatePickerWidget(
              controller: _fechaNacimientoController,
              label: "Fecha de nacimiento",
              onDateSelected: (selectedDate) {
                setState(() {
                  _selectedDate = selectedDate;
                  _fechaNacimientoController.text =
                      "${selectedDate.day}/${selectedDate.month}/${selectedDate.year}";
                });
              },
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _direccionController,
              label: "Dirección",
              icon: Icons.home,
              textInputAction: TextInputAction.next,
              keyboardType: TextInputType.streetAddress,
              validatorType: ValidatorType.name,
            ),
            const SizedBox(height: 16),
            DropdownButtonFormField<TipoTrabajador>(
              value: _tipoTrabajador,
              decoration: InputDecoration(
                labelText: "Tipo de trabajador",
                border: OutlineInputBorder(),
                prefixIcon: Icon(Icons.work),
              ),
              items:
                  TipoTrabajador.values.map((tipo) {
                    return DropdownMenuItem<TipoTrabajador>(
                      value: tipo,
                      child: Text(tipo.name),
                    );
                  }).toList(),
              onChanged: (value) {
                setState(() {
                  _tipoTrabajador = value;
                });
              },
              validator: (value) {
                if (value == null) {
                  return 'Por favor seleccione un tipo';
                }
                return null;
              },
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTimeField(
              context: context,
              controller: _horaInicioController,
              label: "Hora de inicio (HH:mm)",
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTimeField(
              context: context,
              controller: _horaFinController,
              label: "Hora de fin (HH:mm)",
            ),
            const SizedBox(height: 24),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _submitForm,
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF7D8C88),
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: const Text("Guardar Trabajador"),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _submitForm() async {
    if (!_formKeyTrabajador.currentState!.validate()) return;

    if (_selectedDate == null || _tipoTrabajador == null) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Por favor complete todos los campos")),
        );
      }
      return;
    }

    try {
      final nuevoTrabajador = TrabajadorPostDTO(
        usuario: UsuarioPostDTO(
          nombre: _nombreController.text,
          contrasena: _contrasenaController.text,
          correo: _correoController.text,
          telefono: _telefonoController.text,
          fechaNacimiento: _selectedDate!,
          tipoUsuario: TipoUsuario.TRABAJADOR,
          activo: true,
        ),
        direccion: _direccionController.text,
        horaInicio: DateFormat("HH:mm").parse(_horaInicioController.text),
        horaFin: DateFormat("HH:mm").parse(_horaFinController.text),
        tipoTrabajador: _tipoTrabajador!,
      );

      await ref.read(registerTrabajadorProvider(nuevoTrabajador).future);

      if (mounted) {
        ref.invalidate(AllTrabajadoresProvider);

        widget.onEnrollmentSuccess?.call();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Trabajador añadido con éxito'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text("Error al añadir trabajador: ${e.toString()}"),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }
}
