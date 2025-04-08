import 'UserGetDTO.dart';

class CustomerGetDTO {
  final UserGetDTO user;

  CustomerGetDTO({
    required this.user,
  });

  factory CustomerGetDTO.fromJson(Map<String, dynamic> json) {
    return CustomerGetDTO(
      user: UserGetDTO.fromJson(json['user']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'user': user.toJson(),
    };
  }
}