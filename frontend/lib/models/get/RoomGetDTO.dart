class RoomGetDTO {
  final int id;
  final String name;

  RoomGetDTO({
    required this.id,
    required this.name,
  });

  factory RoomGetDTO.fromJson(Map<String, dynamic> json) {
    return RoomGetDTO(
      id: json['id'],
      name: json['name'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
    };
  }
}