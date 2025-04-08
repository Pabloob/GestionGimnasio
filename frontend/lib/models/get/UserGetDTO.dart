import 'package:frontend/models/enums.dart';

class UserGetDTO {
  final int id;
  final String name;
  final String email;
  final String phone;
  final DateTime birthDate;
  final DateTime registrationDate;
  final UserType userType;
  final bool active;

  UserGetDTO({
    required this.id,
    required this.name,
    required this.email,
    required this.phone,
    required this.birthDate,
    required this.registrationDate,
    required this.userType,
    required this.active,
  });

  factory UserGetDTO.fromJson(Map<String, dynamic> json) {
    return UserGetDTO(
      id: json['id'],
      name: json['name'],
      email: json['email'],
      phone: json['phone'],
      birthDate: DateTime.parse(json['birthDate']),
      registrationDate: DateTime.parse(json['registrationDate']),
      userType: UserType.values.firstWhere((e) => e.name == json['userType']),
      active: json['active'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'email': email,
      'phone': phone,
      'birthDate': birthDate.toIso8601String(),
      'registrationDate': registrationDate.toIso8601String(),
      'userType': userType.name,
      'active': active,
    };
  }
}