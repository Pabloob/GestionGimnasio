// customer_controller.dart
import '../models/get/CustomerGetDTO.dart';
import '../models/post/CustomerPostDTO.dart';
import '../models/put/CustomerPutDTO.dart';
import 'api_service.dart';

class CustomerController {
  final ApiService _apiService;

  CustomerController({required ApiService apiService}) : _apiService = apiService;

  Future<List<CustomerGetDTO>> getAllCustomers() async {
    final response = await _apiService.get('/api/customers');
    return (response as List).map((e) => CustomerGetDTO.fromJson(e)).toList();
  }

  Future<CustomerGetDTO> getCustomerById(int id) async {
    final response = await _apiService.get('/api/customers/$id');
    return CustomerGetDTO.fromJson(response);
  }

  Future<CustomerGetDTO> createCustomer(CustomerPostDTO customerPostDTO) async {
    final response = await _apiService.post(
      '/api/customers',
      customerPostDTO.toJson(),
      requiresAuth: false
    );
    return CustomerGetDTO.fromJson(response);
  }

  Future<CustomerGetDTO> updateCustomer(int id, CustomerPutDTO customerPutDTO) async {
    final response = await _apiService.put(
      '/api/customers/$id',
      customerPutDTO.toJson(),
    );
    return CustomerGetDTO.fromJson(response);
  }

  Future<void> deleteCustomer(int id) async {
    await _apiService.delete('/api/customers/$id');
  }
}