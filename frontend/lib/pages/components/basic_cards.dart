import 'package:flutter/material.dart';

import '../../models/get/FitnessClassGetDTO.dart';
import '../../models/get/RoomGetDTO.dart';
import '../../models/get/ScheduleGetDTO.dart';
import '../../models/get/StaffMemberGetDTO.dart';
import '../../theme/app_theme.dart';

Widget buildClassCard(FitnessClassGetDTO clase) {
  return Card(
    elevation: 2,
    margin: const EdgeInsets.symmetric(vertical: 8),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
    ),
    child: ListTile(
      contentPadding: AppTheme.cardPadding,
      leading: CircleAvatar(
        backgroundColor: AppTheme.primaryColor,
        child: const Icon(Icons.fitness_center, color: Colors.white),
      ),
      title: Text(
        clase.name,
        style: AppTheme.cardTitleStyle,
      ),
      subtitle: Text(
        '${clase.price} € - ${clase.maxCapacity} personas',
        style: AppTheme.cardSubtitleStyle,
      ),
      trailing: ElevatedButton(
        onPressed: () {},
        style: ElevatedButton.styleFrom(
          backgroundColor: AppTheme.primaryColor,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
          ),
          padding: const EdgeInsets.all(12),
        ),
        child: const Icon(Icons.edit, color: Colors.white),
      ),
    ),
  );
}

Widget buildWorkerCard(StaffMemberGetDTO trabajador) {
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
                child: const Icon(Icons.person, color: Colors.white),
              ),
              const SizedBox(width: 16),
              Expanded(
                child: Text(
                  trabajador.user.name,
                  style: AppTheme.cardTitleStyle,
                ),
              ),
              ElevatedButton(
                onPressed: () {},
                style: ElevatedButton.styleFrom(
                  backgroundColor: AppTheme.primaryColor,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
                  ),
                  padding: const EdgeInsets.all(12),
                ),
                child: const Icon(Icons.edit, color: Colors.white),
              ),
            ],
          ),
          const SizedBox(height: 8),
          _buildInfoRow('Rol', trabajador.staffType.name),
          _buildInfoRow('Correo', trabajador.user.email),
          _buildInfoRow('Teléfono', trabajador.user.phone),
          _buildInfoRow('Horario', trabajador.getSchedule()),
        ],
      ),
    ),
  );
}

Widget buildHorarioCard(ScheduleGetDTO horario) {
  return Card(
    elevation: 2,
    margin: const EdgeInsets.symmetric(vertical: 8),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
    ),
    child: Padding(
      padding: AppTheme.cardPadding,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          CircleAvatar(
            backgroundColor: AppTheme.primaryColor,
            child: const Icon(Icons.schedule, color: Colors.white),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  horario.fitnessClass.name,
                  style: AppTheme.cardTitleStyle,
                ),
                const SizedBox(height: 8),
                _buildInfoRow('Día', horario.dayOfWeek.name),
                _buildInfoRow('Horario', horario.getHoras()),
                _buildInfoRow('Sala', horario.room.name),
                _buildInfoRow('Instructor', horario.instructor.user.name),
              ],
            ),
          ),
          ElevatedButton(
            onPressed: () {},
            style: ElevatedButton.styleFrom(
              backgroundColor: AppTheme.primaryColor,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
              ),
              padding: const EdgeInsets.all(12),
            ),
            child: const Icon(Icons.edit, color: Colors.white),
          ),
        ],
      ),
    ),
  );
}

Widget buildSalaCard(RoomGetDTO sala) {
  return Card(
    elevation: 2,
    margin: const EdgeInsets.symmetric(vertical: 8),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
    ),
    child: ListTile(
      contentPadding: AppTheme.cardPadding,
      leading: CircleAvatar(
        backgroundColor: AppTheme.primaryColor,
        child: const Icon(Icons.room, color: Colors.white),
      ),
      title: Text(
        sala.name,
        style: AppTheme.cardTitleStyle,
      ),
      trailing: ElevatedButton(
        onPressed: () {},
        style: ElevatedButton.styleFrom(
          backgroundColor: AppTheme.primaryColor,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
          ),
          padding: const EdgeInsets.all(12),
        ),
        child: const Icon(Icons.edit, color: Colors.white),
      ),
    ),
  );
}

Widget _buildInfoRow(String label, String value) {
  return Padding(
    padding: const EdgeInsets.only(bottom: 4),
    child: Row(
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
    ),
  );
}