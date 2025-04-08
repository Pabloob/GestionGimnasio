import 'package:frontend/models/enums.dart';

import 'UserPutDTO.dart';

class StaffMemberPutDTO {
  final UserPutDTO? userPutDTO;
  final String? address;
  final DateTime? startTime;
  final DateTime? endTime;
  final StaffType? staffType;

  StaffMemberPutDTO({
    this.userPutDTO,
    this.address,
    this.startTime,
    this.endTime,
    this.staffType,
  });

  factory StaffMemberPutDTO.fromJson(Map<String, dynamic> json) {
    return StaffMemberPutDTO(
      userPutDTO: json['userPutDTO'] != null
          ? UserPutDTO.fromJson(json['userPutDTO'])
          : null,
      address: json['address'],
      startTime: json['startTime'] != null ? DateTime.parse(json['startTime']) : null,
      endTime: json['endTime'] != null ? DateTime.parse(json['endTime']) : null,
      staffType: json['staffType'] != null
          ? StaffType.values.firstWhere((e) => e.name == json['staffType'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (userPutDTO != null) 'userPutDTO': userPutDTO!.toJson(),
      if (address != null) 'address': address,
      if (startTime != null) 'startTime': startTime!.toIso8601String(),
      if (endTime != null) 'endTime': endTime!.toIso8601String(),
      if (staffType != null) 'staffType': staffType!.name,
    };
  }
}