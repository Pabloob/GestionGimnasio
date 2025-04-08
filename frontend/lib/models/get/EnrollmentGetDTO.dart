import 'CustomerGetDTO.dart';
import 'FitnessClassGetDTO.dart';

class EnrollmentGetDTO {
  final int id;
  final CustomerGetDTO customer;
  final FitnessClassGetDTO fitnessClass;
  final DateTime registrationDate;
  final bool attended;

  EnrollmentGetDTO({
    required this.id,
    required this.customer,
    required this.fitnessClass,
    required this.registrationDate,
    required this.attended,
  });

  factory EnrollmentGetDTO.fromJson(Map<String, dynamic> json) {
    return EnrollmentGetDTO(
      id: json['id'],
      customer: CustomerGetDTO.fromJson(json['customer']),
      fitnessClass: FitnessClassGetDTO.fromJson(json['fitnessClass']),
      registrationDate: DateTime.parse(json['registrationDate']),
      attended: json['attended'],
    );
  }

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