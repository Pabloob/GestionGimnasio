import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/models/get/CustomerGetDTO.dart';
import 'package:frontend/models/put/CustomerPutDTO.dart';
import 'package:frontend/models/put/UserPutDTO.dart';
import 'package:frontend/providers/cliente_providers.dart';
import 'package:frontend/providers/common_providers.dart';
import 'package:frontend/theme/app_theme.dart';

import '../../utils/authService.dart';

class ClientEditProfile extends ConsumerStatefulWidget {
  const ClientEditProfile({super.key});

  @override
  ConsumerState<ClientEditProfile> createState() => _ClientEditProfileState();
}

class _ClientEditProfileState extends ConsumerState<ClientEditProfile> {
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    final userAsync = ref.watch(userProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Editar perfil'),
        elevation: 0,
      ),
      body: userAsync.when(
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (error, _) => Center(
          child: Text('Error: $error', style: AppTheme.errorText),
        ),
        data: (user) => _buildEditForm(user, context),
      ),
    );
  }

  Widget _buildEditForm(CustomerGetDTO user, BuildContext context) {
    return Form(
      key: _formKey,
      child: ListView(
        padding: AppTheme.defaultPadding,
        children: [
          _buildEditTile(
            context: context,
            icon: Icons.person,
            title: 'Nombre',
            value: user.user.name,
            validator: (value) => value?.isEmpty ?? true ? 'Campo requerido' : null,
            onSave: (newValue) => _updateName(newValue),
          ),
          const Divider(height: 1),
          _buildEditTile(
            context: context,
            icon: Icons.email,
            title: 'Correo electrónico',
            value: user.user.email,
            validator: (value) {
              if (value?.isEmpty ?? true) return 'Campo requerido';
              if (!RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$').hasMatch(value!)) {
                return 'Correo no válido';
              }
              return null;
            },
            onSave: (newValue) => _updateEmail(newValue),
          ),
          const Divider(height: 1),
          _buildEditTile(
            context: context,
            icon: Icons.phone,
            title: 'Teléfono',
            value: user.user.phone,
            validator: (value) => value?.isEmpty ?? true ? 'Campo requerido' : null,
            onSave: (newValue) => _updatePhone(newValue),
          ),
          const Divider(height: 1),
          _buildEditTile(
            context: context,
            icon: Icons.lock,
            title: 'Contraseña',
            isPassword: true,
            validator: (value) {
              if (value?.isEmpty ?? true) return 'Campo requerido';
              if (value!.length < 6) return 'Mínimo 6 caracteres';
              return null;
            },
            onSave: (newValue) => _updatePassword(newValue),
          ),
        ],
      ),
    );
  }

  Widget _buildEditTile({
    required BuildContext context,
    required IconData icon,
    required String title,
    String? value,
    bool isPassword = false,
    String? Function(String?)? validator,
    required Future<bool> Function(String) onSave,
  }) {
    return ListTile(
      leading: Icon(icon, color: AppTheme.primaryColor),
      title: Text(title, style: AppTheme.subtitleStyle),
      subtitle: value != null ? Text(value) : null,
      trailing: const Icon(Icons.arrow_forward_ios, size: 16),
      onTap: () => _showEditDialog(
        context,
        title: title,
        initialValue: value ?? '',
        isPassword: isPassword,
        validator: validator,
        onSave: onSave,
      ),
    );
  }

  void _showEditDialog(
      BuildContext context, {
        required String title,
        required String initialValue,
        required bool isPassword,
        String? Function(String?)? validator,
        required Future<bool> Function(String) onSave,
      }) {
    final controller = TextEditingController(text: initialValue);
    final formKey = GlobalKey<FormState>();

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Editar $title', style: AppTheme.dialogTitleStyle),
        content: Form(
          key: formKey,
          child: TextFormField(
            controller: controller,
            obscureText: isPassword,
            decoration: InputDecoration(
              hintText: 'Nuevo $title',
              border: const OutlineInputBorder(),
            ),
            validator: validator,
            autovalidateMode: AutovalidateMode.onUserInteraction,
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancelar'),
          ),
          TextButton(
            onPressed: () async {
              if (formKey.currentState?.validate() ?? false) {
                final success = await onSave(controller.text);
                if (success && context.mounted) {
                  Navigator.pop(context);
                  _showRestartAppDialog(context);
                }
              }
            },
            child: const Text('Guardar'),
          ),
        ],
      ),
    );
  }

  void _showRestartAppDialog(BuildContext context) {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
        title: const Text('Actualización exitosa'),
        content: const Text(
          'Los cambios se aplicarán después de reiniciar la aplicación.',
        ),
        actions: [
          TextButton(
            onPressed: () => AuthService().logout(context),
            child: const Text('Reiniciar ahora'),
          ),
        ],
      ),
    );
  }

  Future<bool> _updateName(String newValue) async {
    return await _saveProfile('nombre', newValue);
  }

  Future<bool> _updateEmail(String newValue) async {
    return await _saveProfile('correo', newValue);
  }

  Future<bool> _updatePhone(String newValue) async {
    return await _saveProfile('telefono', newValue);
  }

  Future<bool> _updatePassword(String newValue) async {
    return await _saveProfile('contraseña', newValue);
  }

  Future<bool> _saveProfile(String field, String value) async {
    try {
      final userId = await AuthService().getUserId();
      if (userId == null) throw Exception('Usuario no autenticado');

      final cliente = CustomerPutDTO(
        userPutDTO: UserPutDTO(
          name: field == 'nombre' ? value.trim() : null,
          password: field == 'contraseña' ? value.trim() : null,
          email: field == 'correo' ? value.trim() : null,
          phone: field == 'telefono' ? value.trim() : null,
        ),
      );

      await ref.read(
        updateClienteProvider(UpdateClienteParams(userId, cliente)).future,
      );
      return true;
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error al actualizar: ${e.toString()}'),
            backgroundColor: Colors.red,
          ),
        );
      }
      return false;
    }
  }
}