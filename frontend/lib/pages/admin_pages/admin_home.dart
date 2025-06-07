import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../models/get/FitnessClassGetDTO.dart';
import '../../models/get/RoomGetDTO.dart';
import '../../models/get/ScheduleGetDTO.dart';
import '../../models/get/StaffMemberGetDTO.dart';
import '../../providers/clase_provider.dart';
import '../../providers/common_providers.dart';
import '../../providers/horario_provider.dart';
import '../../providers/sala_provider.dart';
import '../../providers/trabajador_provider.dart';
import '../../theme/app_theme.dart';
import '../components/basic_cards.dart';
import '../components/common_widgets.dart';

class AdminHomePage extends ConsumerStatefulWidget {
  const AdminHomePage({super.key});

  @override
  ConsumerState<AdminHomePage> createState() => _AdminHomePageState();
}

class _AdminHomePageState extends ConsumerState<AdminHomePage> {
  final List<Section> _sections = [
    Section(
      id: 'trabajadores',
      name: 'Trabajadores',
      icon: Icons.person,
      provider: AllTrabajadoresProvider,
      builder: (data) => buildWorkerCard(data as StaffMemberGetDTO),
    ),
    Section(
      id: 'clases',
      name: 'Clases',
      icon: Icons.fitness_center,
      provider: AllClassesProvider,
      builder: (data) => buildClassCard(data as FitnessClassGetDTO),
    ),
    Section(
      id: 'horarios',
      name: 'Horarios',
      icon: Icons.schedule,
      provider: AllHorariosProvider,
      builder: (data) => buildHorarioCard(data as ScheduleGetDTO),
    ),
    Section(
      id: 'salas',
      name: 'Salas',
      icon: Icons.room,
      provider: AllSalasProvider,
      builder: (data) => buildSalaCard(data as RoomGetDTO),
    ),
  ];

  String _currentSection = 'trabajadores';

  @override
  Widget build(BuildContext context) {
    final userAsync = ref.watch(userProvider);
    final currentSection = _sections.firstWhere((s) => s.id == _currentSection);
    final contentAsync = ref.watch(currentSection.provider);

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
                    user: user.user,
                    textoPrincipal: currentSection.name,
                  ),

                  // Botones de navegación
                  _buildSectionButtons(),
                  const SizedBox(height: 16),

                  // Contenido dinámico
                  _buildContent(currentSection, contentAsync),
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

  Widget _buildContent(
    Section section,
    AsyncValue<List<dynamic>> contentAsync,
  ) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 15),
      child: contentAsync.when(
        loading: () => const Center(child: CircularProgressIndicator()),
        error:
            (error, _) =>
                Center(child: Text('Error: $error', style: AppTheme.errorText)),
        data:
            (data) =>
                data.isEmpty
                    ? Center(
                      child: Text(
                        'No hay ${section.name.toLowerCase()} disponibles.',
                        style: AppTheme.cardSubtitleStyle,
                      ),
                    )
                    : ListView.separated(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: data.length,
                      separatorBuilder: (_, __) => const SizedBox(height: 12),
                      itemBuilder:
                          (context, index) => section.builder(data[index]),
                    ),
      ),
    );
  }
}

class Section {
  final String id;
  final String name;
  final AutoDisposeFutureProvider<List<dynamic>> provider;
  final Widget Function(dynamic) builder;
  final IconData icon;

  Section({
    required this.id,
    required this.name,
    required this.provider,
    required this.builder,
    required this.icon,
  });
}
