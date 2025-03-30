class LoginResponse {
  final String token;
  final String tokenType;
  final String role;
  final Map<String, dynamic> userDetails;

  LoginResponse({
    required this.token,
    required this.tokenType,
    required this.role,
    required this.userDetails,
  });

  // Factory para crear una instancia desde JSON
  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      token: json['token'],
      tokenType: json['tokenType'],
      role: json['role'],
      userDetails: json['userDetails'],
    );
  }
}