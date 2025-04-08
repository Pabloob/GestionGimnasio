// fitness_class_controller.dart


import '../models/get/FitnessClassGetDTO.dart';
import '../models/post/FitnessClassPostDTO.dart';
import '../models/put/FitnessClassPutDTO.dart';
import 'api_service.dart';

class FitnessClassController {
  final ApiService _apiService;

  FitnessClassController({required ApiService apiService}) : _apiService = apiService;

  Future<List<FitnessClassGetDTO>> getAllClasses() async {
    final response = await _apiService.get('/api/classes');
    return (response as List).map((e) => FitnessClassGetDTO.fromJson(e)).toList();
  }

  Future<List<FitnessClassGetDTO>> getActiveClasses() async {
    final response = await _apiService.get('/api/classes/active');
    return (response as List).map((e) => FitnessClassGetDTO.fromJson(e)).toList();
  }

  Future<FitnessClassGetDTO> getClassById(int id) async {
    final response = await _apiService.get('/api/classes/$id');
    return FitnessClassGetDTO.fromJson(response);
  }

  Future<int> getAvailableSpots(int id) async {
    final response = await _apiService.get('/api/classes/$id/available-spots');
    return response as int;
  }

  Future<bool> hasAvailableSpots(int id) async {
    final response = await _apiService.get('/api/classes/$id/has-available-spots');
    return response as bool;
  }

  Future<FitnessClassGetDTO> createClass(FitnessClassPostDTO dto) async {
    final response = await _apiService.post(
      '/api/classes',
      dto.toJson(),
    );
    return FitnessClassGetDTO.fromJson(response);
  }

  Future<FitnessClassGetDTO> updateClass(int id, FitnessClassPutDTO dto) async {
    final response = await _apiService.put(
      '/api/classes/$id',
      dto.toJson(),
    );
    return FitnessClassGetDTO.fromJson(response);
  }

  Future<void> deleteClass(int id) async {
    await _apiService.delete('/api/classes/$id');
  }

  Future<void> toggleClassStatus(int id) async {
    await _apiService.patch('/api/classes/$id/toggle-status');
  }
}