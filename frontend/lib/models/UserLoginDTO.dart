class UserLoginDTO {
  final String email;
  final String password;

  UserLoginDTO({
    required this.email,
    required this.password,
  });

  factory UserLoginDTO.fromJson(Map<String, dynamic> json) {
    return UserLoginDTO(
      email: json['email'],
      password: json['password'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'email': email,
      'password': password,
    };
  }
}