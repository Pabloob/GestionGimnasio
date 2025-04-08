// user_controller.dart
import 'dart:io';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';

import '../models/UserLoginDTO.dart';
import '../models/get/UserGetDTO.dart';
import 'api_service.dart';

class UserController {
  final ApiService _apiService;
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  UserController({required ApiService apiService}) : _apiService = apiService;

  Future<dynamic> login(UserLoginDTO loginRequest) async {
    try {
      final response = await _apiService.post(
        '/api/users/login',
        loginRequest.toJson(),
        requiresAuth: false,
      );

      if (response.containsKey('token')) {
        await _storage.write(key: 'jwt_token', value: response['token']);
        return response;
      } else {
        throw HttpException('Authentication error: Token not found.');
      }
    } catch (e) {
      rethrow;
    }
  }

  Future<UserGetDTO> getUserById(int id) async {
    final response = await _apiService.get('/api/users/$id');
    return UserGetDTO.fromJson(response);
  }

  Future<UserGetDTO> getUserByEmail(String email) async {
    final response = await _apiService.get('/api/users/email/$email');
    return UserGetDTO.fromJson(response);
  }

  Future<List<UserGetDTO>> getAllUsers() async {
    final response = await _apiService.get('/api/users');
    return (response as List).map((e) => UserGetDTO.fromJson(e)).toList();
  }

  Future<bool> checkEmailExists(String email) async {
    final response = await _apiService.get('/api/users/exists/$email');
    return response as bool;
  }

  Future<void> toggleUserStatus(int id) async {
    await _apiService.patch('/api/users/$id/toggle-status');
  }

  Future<void> logout() async {
    await _storage.delete(key: 'jwt_token');
  }
}
