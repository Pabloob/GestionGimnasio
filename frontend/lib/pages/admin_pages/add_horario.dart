import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/enums.dart';
import 'package:frontend/models/get/FitnessClassGetDTO.dart';
import 'package:frontend/models/get/RoomGetDTO.dart';
import 'package:frontend/models/get/StaffMemberGetDTO.dart';
import 'package:frontend/models/post/SchedulePostDTO.dart';
import 'package:frontend/providers/clase_provider.dart';
import 'package:frontend/providers/horario_provider.dart';
import 'package:frontend/providers/sala_provider.dart';
import 'package:frontend/providers/trabajador_provider.dart';
import 'package:intl/intl.dart';

import '../components/date_picker.dart';
import '../components/common_widgets.dart';

class AddHorarioForm extends ConsumerStatefulWidget {
  final VoidCallback? onEnrollmentSuccess;

  const AddHorarioForm({super.key, this.onEnrollmentSuccess});

  @override
  ConsumerState<AddHorarioForm> createState() => _AddHorarioFormState();
}

class _AddHorarioFormState extends ConsumerState<AddHorarioForm> {
  final TextEditingController _horaInicioController = TextEditingController();
  final TextEditingController _horaFinController = TextEditingController();
  final TextEditingController _fechaDeInicioController =
  TextEditingController();
  final TextEditingController _fechaDeFinController = TextEditingController();

  DateTime? _selectedFechaInicio;
  DateTime? _selectedFechaFin;

  final _formKey = GlobalKey<FormState>();

  final Set<DayOfWeek> _diasSeleccionados = {};
  int? _salaId;
  int? _instructorId;
  int? _claseId;

  @override
  void dispose() {
    _horaInicioController.dispose();
    _horaFinController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final instructoresAsync = ref.watch(AllTrabajadoresProvider);
    final clasesAsync = ref.watch(AllClassesProvider);
    final salasAsync = ref.watch(AllSalasProvider);

    return instructoresAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (error, _) => Center(child: Text("Error: $error")),
      data: (instructores) {
        return clasesAsync.when(
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (error, _) => Center(child: Text("Error: $error")),
          data: (clases) {
            return salasAsync.when(
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (error, _) => Center(child: Text("Error: $error")),
              data: (salas) {
                return _buildFormulario(instructores, clases, salas);
              },
            );
          },
        );
      },
    );
  }

  Widget _buildFormulario(
      List<StaffMemberGetDTO> instructores,
      List<FitnessClassGetDTO> clases,
      List<RoomGetDTO> salas,
      ) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Form(
        key: _formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildDiasSemanaSelector(),
            const SizedBox(height: 16),
            _buildDropdown(
              value: _instructorId,
              items: instructores,
              label: "Seleccionar Instructor",
              onChanged: (value) => setState(() => _instructorId = value),
              itemBuilder: (instructor) => instructor.user.name,
              valueBuilder: (instructor) => instructor.user.id,
            ),
            const SizedBox(height: 16),
            _buildDropdown(
              value: _claseId,
              items: clases,
              label: "Seleccionar Clase",
              onChanged: (value) => setState(() => _claseId = value),
              itemBuilder: (clase) => clase.name,
              valueBuilder: (clase) => clase.id,
            ),
            const SizedBox(height: 16),
            _buildDropdown(
              value: _salaId,
              items: salas,
              label: "Seleccionar Sala",
              onChanged: (value) => setState(() => _salaId = value),
              itemBuilder: (sala) => sala.name,
              valueBuilder: (sala) => sala.id,
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
            const SizedBox(height: 16),
            CommonWidgets.buildTimeField(
              context: context,
              controller: _horaFinController,
              label: "Hora de fin (HH:mm)",
            ),
            const SizedBox(height: 24),
            DatePickerWidget(
              controller: _fechaDeInicioController,
              label: "Fecha de inicio",
              onDateSelected: (selectedDate) {
                setState(() {
                  _selectedFechaInicio = selectedDate;
                  _fechaDeInicioController.text =
                  "${_selectedFechaInicio!.day}/${_selectedFechaInicio!.month}/${_selectedFechaInicio!.year}";
                });
              },
              future: true,
            ),
            const SizedBox(height: 24),
            DatePickerWidget(
              controller: _fechaDeFinController,
              label: "Fecha de fin",
              onDateSelected: (selectedDate) {
                setState(() {
                  _selectedFechaFin = selectedDate;
                  _fechaDeFinController.text =
                  "${_selectedFechaFin!.day}/${_selectedFechaFin!.month}/${_selectedFechaFin!.year}";
                });
              },
              future: true,
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
                child: const Text("Guardar Horario"),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _submitForm() async {
    if (!_formKey.currentState!.validate()) return;
    if (_diasSeleccionados.isEmpty ||
        _claseId == null ||
        _salaId == null ||
        _instructorId == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Todos los campos son obligatorios"),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    try {
      final horaInicio = DateFormat("HH:mm").parse(_horaInicioController.text);
      final horaFin = DateFormat("HH:mm").parse(_horaFinController.text);

      final fechaDeInicio = DateFormat(
        'dd/MM/yyyy',
      ).parse(_fechaDeInicioController.text);

      final fechaDeFin = DateFormat(
        'dd/MM/yyyy',
      ).parse(_fechaDeFinController.text);

      for (final dia in _diasSeleccionados) {
        final nuevoHorario = SchedulePostDTO(
          classId: _claseId!,
          dayOfWeek: dia,
          startTime: horaInicio,
          endTime: horaFin,
          roomId: _salaId!,
          instructorId: _instructorId!,
          startDate: fechaDeInicio,
          endDate: fechaDeFin,
        );
        await ref.read(registerHorarioProvider(nuevoHorario).future);
      }

      if (mounted) {
        ref.invalidate(AllHorariosProvider);

        widget.onEnrollmentSuccess?.call();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Horario añadido con éxito'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text("Error al crear horarios: ${e.toString()}"),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  Widget _buildDiasSemanaSelector() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          "Días de la semana:",
          style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 8),
        Wrap(
          spacing: 8,
          runSpacing: 8,
          children:
          DayOfWeek.values.map((dia) {
            final isSelected = _diasSeleccionados.contains(dia);
            return FilterChip(
              label: Text(dia.name),
              selected: isSelected,
              onSelected: (selected) {
                setState(() {
                  selected
                      ? _diasSeleccionados.add(dia)
                      : _diasSeleccionados.remove(dia);
                });
              },
              selectedColor: const Color(0xFF7D8C88),
              checkmarkColor: Colors.white,
              labelStyle: TextStyle(
                color: isSelected ? Colors.white : Colors.black,
              ),
            );
          }).toList(),
        ),
      ],
    );
  }

  Widget _buildDropdown<T>({
    required int? value,
    required List<T> items,
    required String label,
    required Function(int?) onChanged,
    required String Function(T) itemBuilder,
    required int Function(T) valueBuilder,
  }) {
    return DropdownButtonFormField<int>(
      decoration: InputDecoration(
        labelText: label,
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
        contentPadding: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 12,
        ),
        filled: true,
        fillColor: Colors.white,
      ),
      value: value,
      isExpanded: true,
      items:
      items.map((item) {
        return DropdownMenuItem<int>(
          value: valueBuilder(item),
          child: Text(itemBuilder(item)),
        );
      }).toList(),
      onChanged: onChanged,
      validator: (value) => value == null ? "Este campo es obligatorio" : null,
    );
  }
}