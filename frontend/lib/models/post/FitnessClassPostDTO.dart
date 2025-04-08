class FitnessClassPostDTO {
  final String name;
  final int maxCapacity;
  final double price;
  final String description;
  final bool active;

  FitnessClassPostDTO({
    required this.name,
    required this.maxCapacity,
    required this.price,
    required this.description,
    this.active = true,
  });

  factory FitnessClassPostDTO.fromJson(Map<String, dynamic> json) {
    return FitnessClassPostDTO(
      name: json['name'],
      maxCapacity: json['maxCapacity'],
      price: json['price'].toDouble(),
      description: json['description'],
      active: json['active'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'maxCapacity': maxCapacity,
      'price': price,
      'description': description,
      'active': active,
    };
  }
}