
import '../enums.dart';
import 'UserGetDTO.dart';

class StaffMemberGetDTO {
  final UserGetDTO user;
  final String address;
  final DateTime startTime;
  final DateTime endTime;
  final StaffType staffType;

  StaffMemberGetDTO({
    required this.user,
    required this.address,
    required this.startTime,
    required this.endTime,
    required this.staffType,
  });

  factory StaffMemberGetDTO.fromJson(Map<String, dynamic> json) {
    return StaffMemberGetDTO(
      user: UserGetDTO.fromJson(json['user']),
      address: json['address'],
      startTime: DateTime.parse(json['startTime']),
      endTime: DateTime.parse(json['endTime']),
      staffType: StaffType.values.firstWhere((e) => e.name == json['staffType']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'user': user.toJson(),
      'address': address,
      'startTime': startTime.toIso8601String(),
      'endTime': endTime.toIso8601String(),
      'staffType': staffType.name,
    };
  }
}