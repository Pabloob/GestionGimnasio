class PaymentPutDTO {
  final int? customerId;
  final double? amount;
  final bool paid;

  PaymentPutDTO({
    this.customerId,
    this.amount,
    this.paid = false,
  });

  factory PaymentPutDTO.fromJson(Map<String, dynamic> json) {
    return PaymentPutDTO(
      customerId: json['customerId'],
      amount: json['amount']?.toDouble(),
      paid: json['paid'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (customerId != null) 'customerId': customerId,
      if (amount != null) 'amount': amount,
      'paid': paid,
    };
  }
}