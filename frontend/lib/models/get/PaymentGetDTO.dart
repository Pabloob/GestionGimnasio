import 'CustomerGetDTO.dart';

class PaymentGetDTO {
  final int id;
  final CustomerGetDTO customer;
  final double amount;
  final DateTime paymentDate;
  final bool paid;

  PaymentGetDTO({
    required this.id,
    required this.customer,
    required this.amount,
    required this.paymentDate,
    required this.paid,
  });

  factory PaymentGetDTO.fromJson(Map<String, dynamic> json) {
    return PaymentGetDTO(
      id: json['id'],
      customer: CustomerGetDTO.fromJson(json['customer']),
      amount: json['amount'].toDouble(),
      paymentDate: DateTime.parse(json['paymentDate']),
      paid: json['paid'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'customer': customer.toJson(),
      'amount': amount,
      'paymentDate': paymentDate.toIso8601String(),
      'paid': paid,
    };
  }
}