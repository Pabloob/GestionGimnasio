class UserPutDTO {
  final String? name;
  final String? password;
  final String? email;
  final String? phone;
  final bool active;

  UserPutDTO({
    this.name,
    this.password,
    this.email,
    this.phone,
    this.active = true,
  });

  factory UserPutDTO.fromJson(Map<String, dynamic> json) {
    return UserPutDTO(
      name: json['name'],
      password: json['password'],
      email: json['email'],
      phone: json['phone'],
      active: json['active'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (name != null) 'name': name,
      if (password != null) 'password': password,
      if (email != null) 'email': email,
      if (phone != null) 'phone': phone,
      'active': active,
    };
  }
}