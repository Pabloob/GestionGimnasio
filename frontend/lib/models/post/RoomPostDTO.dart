class RoomPostDTO {
  final String name;

  RoomPostDTO({
    required this.name,
  });

  factory RoomPostDTO.fromJson(Map<String, dynamic> json) {
    return RoomPostDTO(
      name: json['name'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
    };
  }
}