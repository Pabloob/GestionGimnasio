import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';

import '../../models/get/FitnessClassGetDTO.dart';
import '../../models/get/ScheduleGetDTO.dart';
import '../../notifications/NotiService.dart';
import '../../providers/horario_provider.dart';
import '../../theme/app_theme.dart';

class ClassCard extends ConsumerStatefulWidget {
  final FitnessClassGetDTO clase;
  final bool isEnrolled;
  final WidgetRef ref;

  const ClassCard({
    required this.clase,
    required this.isEnrolled,
    required this.ref,
    super.key,
  });

  @override
  ConsumerState<ClassCard> createState() => _ClassCardState();
}

class _ClassCardState extends ConsumerState<ClassCard> {
  int selectedDayIndex = 0;

  @override
  Widget build(BuildContext context) {
    final horariosAsync = widget.ref.watch(getByClase(widget.clase.id));

    return Card(
      elevation: 2,
      margin: const EdgeInsets.symmetric(vertical: 8),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
      ),
      child: Padding(
        padding: AppTheme.cardPadding,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                CircleAvatar(
                  backgroundColor: AppTheme.primaryColor,
                  child: const Icon(Icons.fitness_center, color: Colors.white),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Text(
                    widget.clase.name,
                    style: AppTheme.cardTitleStyle,
                  ),
                ),
                IconButton(
                  onPressed: _scheduleNotification,
                  icon: const Icon(Icons.notifications_active, color: Colors.white),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppTheme.primaryColor,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            horariosAsync.when(
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (error, _) => Center(
                child: Text('Error: $error', style: AppTheme.errorText),
              ),
              data: (horariosList) {
                if (horariosList.isEmpty) {
                  return Center(
                    child: Text(
                      'No hay horarios disponibles',
                      style: AppTheme.cardSubtitleStyle,
                    ),
                  );
                }

                return Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SingleChildScrollView(
                      scrollDirection: Axis.horizontal,
                      child: Row(
                        children: List.generate(horariosList.length, (index) {
                          return Padding(
                            padding: const EdgeInsets.only(right: 8),
                            child: ChoiceChip(
                              label: Text(
                                horariosList[index].dayOfWeek.name,
                                style: TextStyle(
                                  color: selectedDayIndex == index
                                      ? Colors.white
                                      : Colors.black,
                                ),
                              ),
                              selected: selectedDayIndex == index,
                              selectedColor: AppTheme.primaryColor,
                              onSelected: (selected) {
                                if (selected) {
                                  setState(() => selectedDayIndex = index);
                                }
                              },
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
                              ),
                            ),
                          );
                        }),
                      ),
                    ),
                    const SizedBox(height: 16),
                    _buildHorarioInfo(horariosList[selectedDayIndex]),
                  ],
                );
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildHorarioInfo(ScheduleGetDTO horario) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _buildInfoRow('Horario', '${_formatTime(horario.startTime)} - ${_formatTime(horario.endTime)}'),
        const SizedBox(height: 4),
        _buildInfoRow('Sala', horario.room.name),
        const SizedBox(height: 4),
        _buildInfoRow('Entrenador', horario.instructor.user.name),
      ],
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          '$label: ',
          style: AppTheme.cardSubtitleStyle.copyWith(
            fontWeight: FontWeight.bold,
          ),
        ),
        Expanded(
          child: Text(
            value,
            style: AppTheme.cardSubtitleStyle,
          ),
        ),
      ],
    );
  }

  String _formatTime(DateTime time) {
    return DateFormat("HH:mm").format(time);
  }

  void _scheduleNotification() {
    Notiservice().scheduleWeeklyNotification(
      id: 1,
      title: "Clase ${widget.clase.name}",
      body: "Te has inscrito a la clase ${widget.clase.name}",
      hour: 16,
      minute: 19,
      weekday: 5,
    );
  }
}