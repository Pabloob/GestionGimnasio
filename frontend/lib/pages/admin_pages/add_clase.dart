import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/post/FitnessClassPostDTO.dart';
import 'package:frontend/providers/clase_provider.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/theme/app_theme.dart';

import '../components/common_widgets.dart';

class AddClaseForm extends ConsumerStatefulWidget {
  final VoidCallback? onEnrollmentSuccess;

  const AddClaseForm({super.key, this.onEnrollmentSuccess});

  @override
  ConsumerState<AddClaseForm> createState() => _AddClaseFormState();
}

class _AddClaseFormState extends ConsumerState<AddClaseForm> {
  final _formKey = GlobalKey<FormState>();
  final _nombreController = TextEditingController();
  final _capacidadController = TextEditingController();
  final _precioController = TextEditingController();
  final _descripcionController = TextEditingController();

  @override
  void dispose() {
    _nombreController.dispose();
    _capacidadController.dispose();
    _precioController.dispose();
    _descripcionController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CommonWidgets.buildTextField(
              controller: _nombreController,
              label: "Nombre de la clase",
              icon: Icons.fitness_center,
              validatorType: ValidatorType.name,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _precioController,
              label: "Precio (€)",
              icon: Icons.euro,
              keyboardType: TextInputType.number,
              validatorType: ValidatorType.number,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _descripcionController,
              label: "Descripción",
              icon: Icons.description,
              validatorType: ValidatorType.name,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 16),
            CommonWidgets.buildTextField(
              controller: _capacidadController,
              label: "Capacidad máxima",
              icon: Icons.group,
              keyboardType: TextInputType.number,
              validatorType: ValidatorType.number,
              textInputAction: TextInputAction.next,
            ),
            const SizedBox(height: 24),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _submitForm,
                style: AppTheme.primaryButtonStyle,
                child: const Text("Guardar Clase"),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _submitForm() async {
    if (!_formKey.currentState!.validate()) return;

    try {
      final nuevaClase = FitnessClassPostDTO(
        name: _nombreController.text,
        maxCapacity: int.parse(_capacidadController.text),
        price: double.parse(_precioController.text),
        description: _descripcionController.text,
        active: true,
      );

      await ref.read(claseServiceProvider).createClass(nuevaClase);

      if (mounted) {
        ref.invalidate(AllClassesProvider);
        widget.onEnrollmentSuccess?.call();

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Clase añadida con éxito'),
            backgroundColor: AppTheme.successColor,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error: ${e.toString()}'),
            backgroundColor: AppTheme.errorColor,
          ),
        );
      }
    }
  }
}