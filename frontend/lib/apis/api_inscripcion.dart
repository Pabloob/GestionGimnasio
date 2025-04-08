// enrollment_controller.dart

import '../models/get/EnrollmentGetDTO.dart';
import '../models/post/EnrollmentPostDTO.dart';
import '../models/put/EnrollmentPutDTO.dart';
import 'api_service.dart';

class EnrollmentController {
  final ApiService _apiService;

  EnrollmentController({required ApiService apiService})
    : _apiService = apiService;

  Future<List<EnrollmentGetDTO>> getAllEnrollments() async {
    final response = await _apiService.get('/api/enrollments');
    return (response as List).map((e) => EnrollmentGetDTO.fromJson(e)).toList();
  }

  Future<EnrollmentGetDTO> getEnrollmentById(int id) async {
    final response = await _apiService.get('/api/enrollments/$id');
    return EnrollmentGetDTO.fromJson(response);
  }

  Future<List<EnrollmentGetDTO>> getEnrollmentsByCustomer(
    int customerId,
  ) async {
    final response = await _apiService.get(
      '/api/enrollments/customer/$customerId',
    );
    return (response as List).map((e) => EnrollmentGetDTO.fromJson(e)).toList();
  }

  Future<List<EnrollmentGetDTO>> getEnrollmentsByClass(int classId) async {
    final response = await _apiService.get('/api/enrollments/class/$classId');
    return (response as List).map((e) => EnrollmentGetDTO.fromJson(e)).toList();
  }

  Future<EnrollmentGetDTO> createEnrollment(
    EnrollmentPostDTO enrollmentPostDTO,
  ) async {
    final response = await _apiService.post(
      '/api/enrollments',
      enrollmentPostDTO.toJson(),
    );
    return EnrollmentGetDTO.fromJson(response);
  }

  Future<EnrollmentGetDTO> updateEnrollment(
    int id,
    EnrollmentPutDTO enrollmentPutDTO,
  ) async {
    final response = await _apiService.put(
      '/api/enrollments/$id',
      enrollmentPutDTO.toJson(),
    );
    return EnrollmentGetDTO.fromJson(response);
  }

  Future<void> deleteEnrollment(int id) async {
    await _apiService.delete('/api/enrollments/$id');
  }

  Future<void> registerAttendance(int id) async {
    await _apiService.patch('/api/enrollments/$id/register-attendance');
  }
}
