class EnrollmentPostDTO {
  final int customerId;
  final int classId;
  final bool attended;

  EnrollmentPostDTO({
    required this.customerId,
    required this.classId,
    this.attended = false,
  });

  factory EnrollmentPostDTO.fromJson(Map<String, dynamic> json) {
    return EnrollmentPostDTO(
      customerId: json['customerId'],
      classId: json['classId'],
      attended: json['attended'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'customerId': customerId,
      'classId': classId,
      'attended': attended,
    };
  }
}