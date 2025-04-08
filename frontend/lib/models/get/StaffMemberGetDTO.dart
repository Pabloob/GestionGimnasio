import 'package:flutter/material.dart';

import '../enums.dart';
import 'UserGetDTO.dart';

class StaffMemberGetDTO {
  final UserGetDTO user;
  final String address;
  final TimeOfDay  startTime;
  final TimeOfDay  endTime;
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
      startTime: TimeOfDay.fromDateTime(
        DateTime.parse("1970-01-01T${json['startTime']}"),
      ),
      endTime: TimeOfDay.fromDateTime(
        DateTime.parse("1970-01-01T${json['endTime']}"),
      ),
      staffType: StaffType.values.firstWhere((e) => e.name == json['staffType']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'user': user.toJson(),
      'address': address,
      'startTime':
      "${startTime.hour.toString().padLeft(2, '0')}:${startTime.minute.toString().padLeft(2, '0')}",
      'endTime':
      "${endTime.hour.toString().padLeft(2, '0')}:${endTime.minute.toString().padLeft(2, '0')}",
      'staffType': staffType.name,
    };
  }

  String getSchedule() {
    return "Schedule: ${startTime.hour}:${startTime.minute} - ${endTime.hour}:${endTime.minute}";
  }
}