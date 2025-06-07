import 'package:intl/intl.dart';

import '../enums.dart';
import 'FitnessClassGetDTO.dart';
import 'RoomGetDTO.dart';
import 'StaffMemberGetDTO.dart';

class ScheduleGetDTO {
  final int id;
  final FitnessClassGetDTO fitnessClass;
  final DayOfWeek dayOfWeek;
  final DateTime startTime;
  final DateTime endTime;
  final RoomGetDTO room;
  final StaffMemberGetDTO instructor;
  final DateTime startDate;
  final DateTime endDate;

  ScheduleGetDTO({
    required this.id,
    required this.fitnessClass,
    required this.dayOfWeek,
    required this.startTime,
    required this.endTime,
    required this.room,
    required this.instructor,
    required this.startDate,
    required this.endDate,
  });

  factory ScheduleGetDTO.fromJson(Map<String, dynamic> json) {
    return ScheduleGetDTO(
      id: json['id'],
      fitnessClass: FitnessClassGetDTO.fromJson(json['fitnessClass']),
      dayOfWeek: DayOfWeek.fromName(json['dayOfWeek']),
      startTime: DateFormat("HH:mm:ss").parse(json['startTime']),
      endTime: DateFormat("HH:mm:ss").parse(json['endTime']),
      room: RoomGetDTO.fromJson(json['room']),
      instructor: StaffMemberGetDTO.fromJson(json['instructor']),
      startDate: DateTime.parse(json['startDate']),
      endDate: DateTime.parse(json['endDate']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'fitnessClass': fitnessClass.toJson(),
      'dayOfWeek': dayOfWeek.value,
      'startTime': DateFormat("HH:mm:ss").format(startTime),
      'endTime': DateFormat("HH:mm:ss").format(endTime),
      'room': room.toJson(),
      'instructor': instructor.toJson(),
      'startDate': startDate.toIso8601String(),
      'endDate': endDate.toIso8601String(),
    };
  }

  String getHoras() {
    return "Horario: ${startTime.hour}:${startTime.minute} - ${endTime.hour}:${endTime.minute}";
  }

  String getFechas() {
    return "Fecha: $startDate - $endDate";
  }
}