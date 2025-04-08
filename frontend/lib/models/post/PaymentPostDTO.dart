class PaymentPostDTO {
  final int customerId;
  final double amount;
  final bool paid;

  PaymentPostDTO({
    required this.customerId,
    required this.amount,
    this.paid = false,
  });

  factory PaymentPostDTO.fromJson(Map<String, dynamic> json) {
    return PaymentPostDTO(
      customerId: json['customerId'],
      amount: json['amount'].toDouble(),
      paid: json['paid'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'customerId': customerId,
      'amount': amount,
      'paid': paid,
    };
  }
}