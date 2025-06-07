
import '../enums.dart';

class UserPostDTO {
  final String name;
  final String password;
  final String email;
  final String phone;
  final DateTime birthDate;
  final UserType userType;
  final bool active;

  UserPostDTO({
    required this.name,
    required this.password,
    required this.email,
    required this.phone,
    required this.birthDate,
    required this.userType,
    this.active = true,
  });

  factory UserPostDTO.fromJson(Map<String, dynamic> json) {
    return UserPostDTO(
      name: json['name'],
      password: json['password'],
      email: json['email'],
      phone: json['phone'],
      birthDate: DateTime.parse(json['birthDate']),
      userType: UserType.values.firstWhere((e) => e.name == json['userType']),
      active: json['active'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'password': password,
      'email': email,
      'phone': phone,
      'birthDate': birthDate.toIso8601String(),
      'userType': userType.name,
      'active': active,
    };
  }
}