import 'package:flutter/material.dart';

import '../../theme/app_theme.dart';

class DatePickerWidget extends StatelessWidget {
  final TextEditingController controller;
  final String label;
  final Function(DateTime) onDateSelected;
  final Future<void> Function(dynamic _)? onFieldSubmitted;
  final bool future;

  const DatePickerWidget({
    super.key,
    required this.controller,
    required this.label,
    required this.onDateSelected,
    this.onFieldSubmitted,
    this.future = false,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => _selectDate(context),
      child: AbsorbPointer(
        child: TextFormField(
          controller: controller,
          onFieldSubmitted: onFieldSubmitted,
          decoration: AppTheme.inputDecoration(label).copyWith(
            prefixIcon: Icon(Icons.calendar_today, color: AppTheme.secondaryColor),
          ),
        ),
      ),
    );
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(1900),
      lastDate: future ? DateTime(2101) : DateTime.now(),
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: ColorScheme.light(primary: AppTheme.primaryColor), dialogTheme: DialogThemeData(backgroundColor: Colors.white),
          ),
          child: child!,
        );
      },
    );

    if (picked != null) {
      onDateSelected(picked);
    }
  }
}