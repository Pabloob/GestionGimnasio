class EnrollmentPutDTO {
  final int? customerId;
  final int? classId;
  final bool attended;

  EnrollmentPutDTO({
    this.customerId,
    this.classId,
    this.attended = false,
  });

  factory EnrollmentPutDTO.fromJson(Map<String, dynamic> json) {
    return EnrollmentPutDTO(
      customerId: json['customerId'],
      classId: json['classId'],
      attended: json['attended'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (customerId != null) 'customerId': customerId,
      if (classId != null) 'classId': classId,
      'attended': attended,
    };
  }
}