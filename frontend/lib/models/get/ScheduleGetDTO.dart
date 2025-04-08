import 'package:frontend/models/get/CustomerGetDTO.dart';

import 'FitnessClassGetDTO.dart';

class ScheduleGetDTO {
  final int id;
  final CustomerGetDTO customer;
  final FitnessClassGetDTO fitnessClass;
  final DateTime registrationDate;
  final bool attended;

  ScheduleGetDTO({
    required this.id,
    required this.customer,
    required this.fitnessClass,
    required this.registrationDate,
    required this.attended,
  });

  // Factory to create an instance from JSON
  factory ScheduleGetDTO.fromJson(Map<String, dynamic> json) {
    return ScheduleGetDTO(
      id: json['id'],
      customer: CustomerGetDTO.fromJson(json['customer']),
      fitnessClass: FitnessClassGetDTO.fromJson(json['fitnessClass']),
      registrationDate: DateTime.parse(json['registrationDate']),
      attended: json['attended'],
    );
  }

  // Method to convert the object to JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'customer': customer.toJson(),
      'fitnessClass': fitnessClass.toJson(),
      'registrationDate': registrationDate.toIso8601String(),
      'attended': attended,
    };
  }
}
