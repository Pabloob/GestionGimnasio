class FitnessClassPutDTO {
  final String? name;
  final int? maxCapacity;
  final double? price;
  final String? description;
  final bool active;

  FitnessClassPutDTO({
    this.name,
    this.maxCapacity,
    this.price,
    this.description,
    this.active = true,
  });

  factory FitnessClassPutDTO.fromJson(Map<String, dynamic> json) {
    return FitnessClassPutDTO(
      name: json['name'],
      maxCapacity: json['maxCapacity'],
      price: json['price']?.toDouble(),
      description: json['description'],
      active: json['active'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (name != null) 'name': name,
      if (maxCapacity != null) 'maxCapacity': maxCapacity,
      if (price != null) 'price': price,
      if (description != null) 'description': description,
      'active': active,
    };
  }
}