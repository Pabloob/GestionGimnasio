// room_controller.dart
import 'package:frontend/models/put/SalaPutDTO.dart';

import '../models/get/RoomGetDTO.dart';
import '../models/post/RoomPostDTO.dart';
import 'api_service.dart';

class RoomController {
  final ApiService _apiService;

  RoomController({required ApiService apiService}) : _apiService = apiService;

  Future<List<RoomGetDTO>> getAllRooms() async {
    final response = await _apiService.get('/api/rooms');
    return (response as List).map((e) => RoomGetDTO.fromJson(e)).toList();
  }

  Future<RoomGetDTO> getRoomById(int id) async {
    final response = await _apiService.get('/api/rooms/$id');
    return RoomGetDTO.fromJson(response);
  }

  Future<RoomGetDTO> createRoom(RoomPostDTO roomPostDTO) async {
    final response = await _apiService.post(
      '/api/rooms',
      roomPostDTO.toJson(),
    );
    return RoomGetDTO.fromJson(response);
  }

  Future<RoomGetDTO> updateRoom(int id, RoomPutDTO roomPutDTO) async {
    final response = await _apiService.put(
      '/api/rooms/$id',
      roomPutDTO.toJson(),
    );
    return RoomGetDTO.fromJson(response);
  }

  Future<void> deleteRoom(int id) async {
    await _apiService.delete('/api/rooms/$id');
  }
}