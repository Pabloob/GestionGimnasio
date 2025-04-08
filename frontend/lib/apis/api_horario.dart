// schedule_controller.dart

import '../models/get/ScheduleGetDTO.dart';
import '../models/post/SchedulePostDTO.dart';
import '../models/put/SchedulePutDTO.dart';
import 'api_service.dart';

class ScheduleController {
  final ApiService _apiService;

  ScheduleController({required ApiService apiService}) : _apiService = apiService;

  Future<List<ScheduleGetDTO>> getAllSchedules() async {
    final response = await _apiService.get('/api/schedules');
    return (response as List).map((e) => ScheduleGetDTO.fromJson(e)).toList();
  }

  Future<ScheduleGetDTO> getScheduleById(int id) async {
    final response = await _apiService.get('/api/schedules/$id');
    return ScheduleGetDTO.fromJson(response);
  }

  Future<List<ScheduleGetDTO>> getSchedulesByClass(int classId) async {
    final response = await _apiService.get('/api/schedules/class/$classId');
    return (response as List).map((e) => ScheduleGetDTO.fromJson(e)).toList();
  }

  Future<List<ScheduleGetDTO>> getSchedulesByRoom(int roomId) async {
    final response = await _apiService.get('/api/schedules/room/$roomId');
    return (response as List).map((e) => ScheduleGetDTO.fromJson(e)).toList();
  }

  Future<List<ScheduleGetDTO>> getSchedulesByInstructor(int instructorId) async {
    final response = await _apiService.get('/api/schedules/instructor/$instructorId');
    return (response as List).map((e) => ScheduleGetDTO.fromJson(e)).toList();
  }

  Future<List<ScheduleGetDTO>> getSchedulesByDay(String day) async {
    final response = await _apiService.get('/api/schedules/day/$day');
    return (response as List).map((e) => ScheduleGetDTO.fromJson(e)).toList();
  }

  Future<ScheduleGetDTO> createSchedule(SchedulePostDTO schedulePostDTO) async {
    final response = await _apiService.post(
      '/api/schedules',
      schedulePostDTO.toJson(),
    );
    return ScheduleGetDTO.fromJson(response);
  }

  Future<ScheduleGetDTO> updateSchedule(int id, SchedulePutDTO schedulePutDTO) async {
    final response = await _apiService.put(
      '/api/schedules/$id',
      schedulePutDTO.toJson(),
    );
    return ScheduleGetDTO.fromJson(response);
  }

  Future<void> deleteSchedule(int id) async {
    await _apiService.delete('/api/schedules/$id');
  }
}