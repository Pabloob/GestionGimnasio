import 'UserPostDTO.dart';

class CustomerPostDTO {
  final UserPostDTO user;

  CustomerPostDTO({
    required this.user,
  });

  factory CustomerPostDTO.fromJson(Map<String, dynamic> json) {
    return CustomerPostDTO(
      user: UserPostDTO.fromJson(json['user']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'user': user.toJson(),
    };
  }
}