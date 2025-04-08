import 'package:frontend/models/enums.dart';

class SchedulePostDTO {
  final int classId;
  final DayOfWeek dayOfWeek;
  final DateTime startTime;
  final DateTime endTime;
  final int roomId;
  final int instructorId;
  final DateTime startDate;
  final DateTime endDate;

  SchedulePostDTO({
    required this.classId,
    required this.dayOfWeek,
    required this.startTime,
    required this.endTime,
    required this.roomId,
    required this.instructorId,
    required this.startDate,
    required this.endDate,
  });

  factory SchedulePostDTO.fromJson(Map<String, dynamic> json) {
    return SchedulePostDTO(
      classId: json['classId'],
      dayOfWeek: DayOfWeek.fromName(json['dayOfWeek']),
      startTime: DateTime.parse(json['startTime']),
      endTime: DateTime.parse(json['endTime']),
      roomId: json['roomId'],
      instructorId: json['instructorId'],
      startDate: DateTime.parse(json['startDate']),
      endDate: DateTime.parse(json['endDate']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'classId': classId,
      'dayOfWeek': dayOfWeek.value,
      'startTime': startTime.toIso8601String(),
      'endTime': endTime.toIso8601String(),
      'roomId': roomId,
      'instructorId': instructorId,
      'startDate': startDate.toIso8601String(),
      'endDate': endDate.toIso8601String(),
    };
  }
}