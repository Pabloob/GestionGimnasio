// payment_controller.dart

import '../models/get/PaymentGetDTO.dart';
import '../models/post/PaymentPostDTO.dart';
import '../models/put/PaymentPutDTO.dart';
import 'api_service.dart';

class PaymentController {
  final ApiService _apiService;

  PaymentController({required ApiService apiService}) : _apiService = apiService;

  Future<List<PaymentGetDTO>> getAllPayments() async {
    final response = await _apiService.get('/api/payments');
    return (response as List).map((e) => PaymentGetDTO.fromJson(e)).toList();
  }

  Future<PaymentGetDTO> getPaymentById(int id) async {
    final response = await _apiService.get('/api/payments/$id');
    return PaymentGetDTO.fromJson(response);
  }

  Future<List<PaymentGetDTO>> getPaymentsByCustomer(int customerId) async {
    final response = await _apiService.get('/api/payments/customer/$customerId');
    return (response as List).map((e) => PaymentGetDTO.fromJson(e)).toList();
  }

  Future<PaymentGetDTO> createPayment(PaymentPostDTO paymentPostDTO) async {
    final response = await _apiService.post(
      '/api/payments',
      paymentPostDTO.toJson(),
    );
    return PaymentGetDTO.fromJson(response);
  }

  Future<PaymentGetDTO> updatePayment(int id, PaymentPutDTO paymentPutDTO) async {
    final response = await _apiService.put(
      '/api/payments/$id',
      paymentPutDTO.toJson(),
    );
    return PaymentGetDTO.fromJson(response);
  }

  Future<void> deletePayment(int id) async {
    await _apiService.delete('/api/payments/$id');
  }

  Future<void> processPayment(int id) async {
    await _apiService.patch('/api/payments/$id/process');
  }

  Future<void> cancelPayment(int id) async {
    await _apiService.patch('/api/payments/$id/cancel');
  }
}