import 'package:flutter/material.dart';
import 'package:frontend/models/post/RoomPostDTO.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/providers/sala_provider.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../components/common_widgets.dart';

class AddSalaForm extends ConsumerStatefulWidget {
  final VoidCallback? onEnrollmentSuccess;
  const AddSalaForm({super.key, this.onEnrollmentSuccess});

  @override
  ConsumerState<AddSalaForm> createState() => _AddSalaFormState();
}

class _AddSalaFormState extends ConsumerState<AddSalaForm> {
  final _formKeyClase = GlobalKey<FormState>();
  final TextEditingController _nombreController = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _nombreController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Form(
        key: _formKeyClase,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CommonWidgets.buildTextField(
              controller: _nombreController,
              icon: Icons.person,
              keyboardType: TextInputType.name,
              textInputAction: TextInputAction.next,
              label: "Nombre de la clase",
              validatorType: ValidatorType.name,
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
                child: const Text("Guardar Clase"),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _submitForm() async {
    if (!_formKeyClase.currentState!.validate()) return;

    try {
      final nuevaSala = RoomPostDTO(name: _nombreController.text);

      await ref.read(salaServiceProvider).createRoom(nuevaSala);

      if (mounted) {
        ref.invalidate(AllSalasProvider);

        widget.onEnrollmentSuccess?.call();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Sala añadida con éxito'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Error al añadir clase: ${e.toString()}")),
        );
      }
    }
  }
}