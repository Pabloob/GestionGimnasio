class FitnessClassGetDTO {
  final int id;
  final String name;
  final int maxCapacity;
  final double price;
  final String description;
  final bool active;

  FitnessClassGetDTO({
    required this.id,
    required this.name,
    required this.maxCapacity,
    required this.price,
    required this.description,
    required this.active,
  });

  factory FitnessClassGetDTO.fromJson(Map<String, dynamic> json) {
    return FitnessClassGetDTO(
      id: json['id'],
      name: json['name'],
      maxCapacity: json['maxCapacity'],
      price: json['price'].toDouble(),
      description: json['description'],
      active: json['active'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'maxCapacity': maxCapacity,
      'price': price,
      'description': description,
      'active': active,
    };
  }
}