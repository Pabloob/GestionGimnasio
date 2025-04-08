// staff_member_controller.dart

import '../models/get/StaffMemberGetDTO.dart';
import '../models/post/TrabajadorPostDTO.dart';
import '../models/put/StaffMemberPutDTO.dart';
import 'api_service.dart';

class StaffMemberController {
  final ApiService _apiService;

  StaffMemberController({required ApiService apiService}) : _apiService = apiService;

  Future<List<StaffMemberGetDTO>> getAllStaffMembers() async {
    final response = await _apiService.get('/api/staff-members');
    return (response as List).map((e) => StaffMemberGetDTO.fromJson(e)).toList();
  }

  Future<StaffMemberGetDTO> getStaffMemberById(int id) async {
    final response = await _apiService.get('/api/staff-members/$id');
    return StaffMemberGetDTO.fromJson(response);
  }

  Future<List<StaffMemberGetDTO>> getStaffMembersByType(String staffType) async {
    final response = await _apiService.get('/api/staff-members/type/$staffType');
    return (response as List).map((e) => StaffMemberGetDTO.fromJson(e)).toList();
  }

  Future<StaffMemberGetDTO> createStaffMember(StaffMemberPostDTO staffMemberPostDTO) async {
    final response = await _apiService.post(
      '/api/staff-members',
      staffMemberPostDTO.toJson(),
    );
    return StaffMemberGetDTO.fromJson(response);
  }

  Future<StaffMemberGetDTO> updateStaffMember(int id, StaffMemberPutDTO staffMemberPutDTO) async {
    final response = await _apiService.put(
      '/api/staff-members/$id',
      staffMemberPutDTO.toJson(),
    );
    return StaffMemberGetDTO.fromJson(response);
  }

  Future<void> deleteStaffMember(int id) async {
    await _apiService.delete('/api/staff-members/$id');
  }

  Future<bool> checkAvailability({
    required int staffMemberId,
    required String day,
    required DateTime startTime,
    required DateTime endTime,
  }) async {
    final response = await _apiService.get(
      '/api/staff-members/availability?staffMemberId=$staffMemberId&day=$day&startTime=${startTime.toIso8601String()}&endTime=${endTime.toIso8601String()}',
    );
    return response as bool;
  }
}