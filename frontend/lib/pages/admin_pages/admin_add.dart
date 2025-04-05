import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/pages/admin_pages/add_clase.dart';
import 'package:frontend/pages/admin_pages/add_horario.dart';
import 'package:frontend/pages/admin_pages/add_sala.dart';
import 'package:frontend/pages/admin_pages/add_trabajador.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/theme/app_theme.dart';

import '../components/common_widgets.dart';

class AdminAddPage extends ConsumerStatefulWidget {
  const AdminAddPage({super.key, this.onEnrollmentSuccess});

  final VoidCallback? onEnrollmentSuccess;

  @override
  ConsumerState<AdminAddPage> createState() => _AdminAddPageState();
}

class _AdminAddPageState extends ConsumerState<AdminAddPage> {
  final List<AddSection> _sections = [
    AddSection(
      id: 'trabajador',
      name: 'Trabajador',
      formTitle: 'Añadir Trabajador',
      icon: Icons.person,
      formBuilder:
          (onSuccess) => AddTrabajadorForm(onEnrollmentSuccess: onSuccess),
    ),
    AddSection(
      id: 'clase',
      name: 'Clase',
      formTitle: 'Añadir Clase',
      icon: Icons.fitness_center,
      formBuilder: (onSuccess) => AddClaseForm(onEnrollmentSuccess: onSuccess),
    ),
    AddSection(
      id: 'horario',
      name: 'Horario',
      formTitle: 'Añadir Horario',
      icon: Icons.schedule,
      formBuilder:
          (onSuccess) => AddHorarioForm(onEnrollmentSuccess: onSuccess),
    ),
    AddSection(
      id: 'sala',
      name: 'Sala',
      formTitle: 'Añadir Sala',
      icon: Icons.room,
      formBuilder: (onSuccess) => AddSalaForm(onEnrollmentSuccess: onSuccess),
    ),
  ];

  String _currentSection = 'trabajador';

  @override
  Widget build(BuildContext context) {
    final userAsync = ref.watch(userProvider);
    final currentSection = _sections.firstWhere((s) => s.id == _currentSection);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error:
          (error, _) =>
              Center(child: Text('Error: $error', style: AppTheme.errorText)),
      data:
          (user) => Scaffold(
            backgroundColor: AppTheme.backgroundColor,
            body: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  CommonWidgets.buildCustomTopMesage(
                    user: user.usuario,
                    textoPrincipal: currentSection.formTitle,
                  ),

                  // Botones de navegación
                  _buildSectionButtons(),
                  const SizedBox(height: 16),

                  // Formulario dinámico
                  currentSection.formBuilder(widget.onEnrollmentSuccess),
                ],
              ),
            ),
          ),
    );
  }

  Widget _buildSectionButtons() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children:
            _sections
                .map(
                  (section) => Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: ElevatedButton.icon(
                      onPressed:
                          () => setState(() => _currentSection = section.id),
                      style: ElevatedButton.styleFrom(
                        backgroundColor:
                            _currentSection == section.id
                                ? AppTheme.primaryColor
                                : Colors.grey,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(
                            AppTheme.defaultRadius,
                          ),
                        ),
                        iconColor: Colors.white,
                      ),
                      icon: Icon(section.icon, size: 20),
                      label: Text(
                        section.name,
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
                )
                .toList(),
      ),
    );
  }
}

class AddSection {
  final String id;
  final String name;
  final String formTitle;
  final IconData icon;
  final Widget Function(VoidCallback? onSuccess) formBuilder;

  AddSection({
    required this.id,
    required this.name,
    required this.formTitle,
    required this.icon,
    required this.formBuilder,
  });
}
