class SchedulePutDTO {
  final int? classId;
  final String? dayOfWeek;
  final DateTime? startTime;
  final DateTime? endTime;
  final int? roomId;
  final int? instructorId;
  final DateTime? startDate;
  final DateTime? endDate;

  SchedulePutDTO({
    this.classId,
    this.dayOfWeek,
    this.startTime,
    this.endTime,
    this.roomId,
    this.instructorId,
    this.startDate,
    this.endDate,
  });

  factory SchedulePutDTO.fromJson(Map<String, dynamic> json) {
    return SchedulePutDTO(
      classId: json['classId'],
      dayOfWeek: json['dayOfWeek'],
      startTime: json['startTime'] != null ? DateTime.parse(json['startTime']) : null,
      endTime: json['endTime'] != null ? DateTime.parse(json['endTime']) : null,
      roomId: json['roomId'],
      instructorId: json['instructorId'],
      startDate: json['startDate'] != null ? DateTime.parse(json['startDate']) : null,
      endDate: json['endDate'] != null ? DateTime.parse(json['endDate']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (classId != null) 'classId': classId,
      if (dayOfWeek != null) 'dayOfWeek': dayOfWeek,
      if (startTime != null) 'startTime': startTime!.toIso8601String(),
      if (endTime != null) 'endTime': endTime!.toIso8601String(),
      if (roomId != null) 'roomId': roomId,
      if (instructorId != null) 'instructorId': instructorId,
      if (startDate != null) 'startDate': startDate!.toIso8601String(),
      if (endDate != null) 'endDate': endDate!.toIso8601String(),
    };
  }
}