import 'package:flutter/material.dart';
import 'package:frontend/models/get/ClaseGetDTO.dart';
import 'package:frontend/models/get/HorarioGetDTO.dart';
import 'package:frontend/models/get/SalaGetDTO.dart';
import 'package:frontend/models/get/TrabajadorGetDTO.dart';
import 'package:frontend/theme/app_theme.dart';

Widget buildClassCard(ClaseGetDTO clase) {
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
        clase.nombre,
        style: AppTheme.cardTitleStyle,
      ),
      subtitle: Text(
        '${clase.precio} € - ${clase.capacidadMaxima} personas',
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

Widget buildWorkerCard(TrabajadorGetDTO trabajador) {
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
                  trabajador.usuario.nombre,
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
          _buildInfoRow('Rol', trabajador.tipoTrabajador.name),
          _buildInfoRow('Correo', trabajador.usuario.correo),
          _buildInfoRow('Teléfono', trabajador.usuario.telefono),
          _buildInfoRow('Horario', trabajador.getHorario()),
        ],
      ),
    ),
  );
}

Widget buildHorarioCard(HorarioGetDTO horario) {
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
                  horario.clase.nombre,
                  style: AppTheme.cardTitleStyle,
                ),
                const SizedBox(height: 8),
                _buildInfoRow('Día', horario.diaSemana.name),
                _buildInfoRow('Horario', horario.getHoras()),
                _buildInfoRow('Sala', horario.sala.nombre),
                _buildInfoRow('Instructor', horario.instructor.usuario.nombre),
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

Widget buildSalaCard(SalaGetDTO sala) {
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
        sala.nombre,
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