import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/HorarioGetDTO.dart';
import 'package:frontend/providers/horario_provider.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/theme/app_theme.dart';
import 'package:table_calendar/table_calendar.dart';

class InstructorHomePage extends ConsumerStatefulWidget {
  const InstructorHomePage({super.key});

  @override
  ConsumerState<InstructorHomePage> createState() => _InstructorHomePageState();
}

class _InstructorHomePageState extends ConsumerState<InstructorHomePage> {
  late Map<DateTime, List<HorarioGetDTO>> _horariosPorDia;
  DateTime _focusedDay = DateTime.now();
  DateTime? _selectedDay;
  DateTime? _firstDay;
  DateTime? _lastDay;

  @override
  void initState() {
    super.initState();
    _horariosPorDia = {};
    _selectedDay = DateTime.now();
  }

  List<HorarioGetDTO> _getHorariosParaDia(DateTime day) {
    return _horariosPorDia[DateTime.utc(day.year, day.month, day.day)] ?? [];
  }

  @override
  Widget build(BuildContext context) {
    final userAsync = ref.watch(userProvider);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (error, _) => Center(
        child: Text('Error: $error', style: AppTheme.errorText),
      ),
      data: (user) {
        final horariosAsync = ref.watch(getByInstructor(user.usuario.id));

        return horariosAsync.when(
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (error, _) => Center(
            child: Text('Error: $error', style: AppTheme.errorText),
          ),
          data: (horarios) {
            _calculateDateRange(horarios);
            _processHorarios(horarios);

            return Column(
              children: [
                _buildCalendar(),
                const SizedBox(height: 16),
                _buildHorariosList(),
              ],
            );
          },
        );
      },
    );
  }

  void _calculateDateRange(List<HorarioGetDTO> horarios) {
    if (horarios.isNotEmpty) {
      _firstDay = horarios
          .map((h) => h.fechasInicio)
          .reduce((a, b) => a.isBefore(b) ? a : b);
      _lastDay = horarios
          .map((h) => h.fechaFin)
          .reduce((a, b) => a.isAfter(b) ? a : b);
    } else {
      _firstDay = DateTime.now();
      _lastDay = DateTime.now().add(const Duration(days: 30));
    }

    if (_selectedDay != null &&
        (_selectedDay!.isBefore(_firstDay!) ||
            _selectedDay!.isAfter(_lastDay!))) {
      _selectedDay = null;
    }
  }

  void _processHorarios(List<HorarioGetDTO> horarios) {
    _horariosPorDia.clear();

    for (var horario in horarios) {
      final currentDay = DateTime(
        horario.fechasInicio.year,
        horario.fechasInicio.month,
        horario.fechasInicio.day,
      );
      final endDay = DateTime(
        horario.fechaFin.year,
        horario.fechaFin.month,
        horario.fechaFin.day,
      );

      DateTime day = currentDay;
      while (day.isBefore(endDay) || day.isAtSameMomentAs(endDay)) {
        if (day.weekday == horario.diaSemana.index + 1) {
          final normalizedDay = DateTime.utc(day.year, day.month, day.day);
          _horariosPorDia.update(
            normalizedDay,
                (list) => list..add(horario),
            ifAbsent: () => [horario],
          );
        }
        day = day.add(const Duration(days: 1));
      }
    }
  }

  Widget _buildCalendar() {
    return Card(
      margin: AppTheme.defaultPadding,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
      ),
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: TableCalendar<HorarioGetDTO>(
          firstDay: _firstDay!,
          lastDay: _lastDay!,
          focusedDay: _focusedDay,
          selectedDayPredicate: (day) => isSameDay(_selectedDay, day),
          eventLoader: _getHorariosParaDia,
          availableCalendarFormats: const {
            CalendarFormat.month: 'Mes',
          },
          calendarStyle: CalendarStyle(
            todayDecoration: BoxDecoration(
              color: AppTheme.primaryColor.withOpacity(0.5),
              shape: BoxShape.circle,
            ),
            selectedDecoration: BoxDecoration(
              color: AppTheme.primaryColor,
              shape: BoxShape.circle,
            ),
          ),
          calendarBuilders: CalendarBuilders(
            markerBuilder: (context, day, events) {
              if (events.isNotEmpty) {
                return Positioned(
                  right: 1,
                  bottom: 1,
                  child: Container(
                    padding: const EdgeInsets.all(4),
                    decoration: const BoxDecoration(
                      shape: BoxShape.circle,
                      color: AppTheme.primaryColor,
                    ),
                    width: 16,
                    height: 16,
                    child: Center(
                      child: Text(
                        events.length.toString(),
                        style: const TextStyle(
                          color: Colors.white,
                          fontSize: 10,
                        ),
                      ),
                    ),
                  ),
                );
              }
              return null;
            },
          ),
          onDaySelected: (selectedDay, focusedDay) {
            setState(() {
              _selectedDay = selectedDay;
              _focusedDay = focusedDay;
            });
          },
        ),
      ),
    );
  }

  Widget _buildHorariosList() {
    final horarios = _getHorariosParaDia(_selectedDay ?? _focusedDay);

    if (horarios.isEmpty) {
      return Center(
        child: Text(
          'No hay clases programadas',
          style: AppTheme.cardSubtitleStyle,
        ),
      );
    }

    return Expanded(
      child: ListView.separated(
        padding: const EdgeInsets.symmetric(horizontal: 16),
        itemCount: horarios.length,
        separatorBuilder: (_, __) => const SizedBox(height: 8),
        itemBuilder: (context, index) {
          final horario = horarios[index];
          return Card(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
            ),
            child: ListTile(
              contentPadding: const EdgeInsets.symmetric(
                horizontal: 16,
                vertical: 12,
              ),
              leading: Container(
                width: 4,
                decoration: BoxDecoration(
                  color: AppTheme.primaryColor,
                  borderRadius: BorderRadius.circular(4),
                ),
              ),
              title: Text(
                horario.clase.nombre,
                style: AppTheme.cardTitleStyle,
              ),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const SizedBox(height: 4),
                  Text(
                    'Sala: ${horario.sala.nombre}',
                    style: AppTheme.cardSubtitleStyle,
                  ),
                  const SizedBox(height: 4),
                  Text(
                    '${_formatTime(horario.horaInicio)} - ${_formatTime(horario.horaFin)}',
                    style: AppTheme.cardSubtitleStyle.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  String _formatTime(DateTime time) {
    return '${time.hour}:${time.minute.toString().padLeft(2, '0')}';
  }
}