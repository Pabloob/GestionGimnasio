import 'package:flutter/material.dart';
import 'package:frontend/apis/api_cliente.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/models/put/ClientePutDTO.dart';
import 'package:frontend/utils/utils.dart';

class ClientEditProfile extends StatefulWidget {
  const ClientEditProfile({super.key});

  @override
  State<ClientEditProfile> createState() => _ClientEditProfileState();
}

class _ClientEditProfileState extends State<ClientEditProfile> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  final ApiCliente _apiCliente = ApiCliente(apiService: ApiService());

  String _mensajeError = "";
  bool _isLoading = false;

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _phoneController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: _buildAppBar(),
        backgroundColor: Colors.transparent,
        elevation: 0,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: SingleChildScrollView(
          child: Form(
            key: _formKey,
            child: Padding(
              padding: EdgeInsets.all(16),
              child: Column(
                children: [
                  // My Account
                  ListTile(
                    leading: Icon(Icons.person),
                    title: Text('Cambiar nombre'),
                    trailing: Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      _showConfirmationDialog(
                        _nameController,
                        'nombre',
                        Icons.person,
                      );
                    },
                  ),
                  Divider(),
                  ListTile(
                    leading: Icon(Icons.lock),
                    title: Text('Cambiar contraseña'),
                    trailing: Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      _showConfirmationDialog(
                        _passwordController,
                        'contraseña',
                        Icons.lock,
                      );
                    },
                  ),
                  Divider(),
                  ListTile(
                    leading: Icon(Icons.email),
                    title: Text('Cambiar correo electronico'),
                    trailing: Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      _showConfirmationDialog(
                        _emailController,
                        'correo electrónico',
                        Icons.email,
                      );
                    },
                  ),
                  Divider(),
                  ListTile(
                    leading: Icon(Icons.phone),
                    title: Text('Cambiar telefono'),
                    trailing: Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      _showConfirmationDialog(
                        _phoneController,
                        'telefono',
                        Icons.phone,
                      );
                    },
                  ),
                  _isLoading
                      ? Center(child: CircularProgressIndicator())
                      : SizedBox(),
                  // Error message for any issues
                  if (_mensajeError.isNotEmpty)
                    Padding(
                      padding: const EdgeInsets.only(top: 16),
                      child: Text(
                        _mensajeError,
                        style: TextStyle(color: Colors.red),
                      ),
                    ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildAppBar() {
    return const Text(
      "Editar Perfil",
      style: TextStyle(
        fontSize: 20,
        fontWeight: FontWeight.bold,
        color: Color(0xfffa6045),
      ),
    );
  }

  void _showConfirmationDialog(
    TextEditingController controller,
    String fieldName,
    IconData icon, {
    bool isPassword = false,
  }) {
    bool isLoading = false;
    String errorMessage = '';

    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) {
        return StatefulBuilder(
          builder: (context, setStateDialog) {
            return AlertDialog(
              title: Text('Confirmar cambio de $fieldName'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  TextFormField(
                    controller: controller,
                    obscureText: isPassword,
                    decoration: InputDecoration(
                      labelText: 'Nuevo $fieldName',
                      prefixIcon: Icon(icon),
                    ),
                  ),
                  if (errorMessage.isNotEmpty)
                    Padding(
                      padding: const EdgeInsets.only(top: 8),
                      child: Text(
                        errorMessage,
                        style: const TextStyle(color: Colors.red),
                      ),
                    ),
                ],
              ),
              actions: [
                TextButton(
                  onPressed: isLoading ? null : () => Navigator.pop(context),
                  child: const Text('Cancelar'),
                ),
                ElevatedButton(
                  onPressed:
                      isLoading
                          ? null
                          : () async {
                            if (controller.text.isEmpty) {
                              setStateDialog(() {
                                errorMessage = 'Por favor ingresa un valor';
                              });
                              return;
                            }

                            setStateDialog(() {
                              isLoading = true;
                              errorMessage = '';
                            });

                            try {
                              final success = await _saveProfile(fieldName);
                              if (success) {
                                Navigator.pop(context);
                                _showRestartAppDialog();
                              } else {
                                setStateDialog(() {
                                  errorMessage = 'Error al actualizar';
                                  isLoading = false;
                                });
                              }
                            } catch (e) {
                              setStateDialog(() {
                                errorMessage = _getUserFriendlyError(e);
                                isLoading = false;
                              });
                            }
                          },
                  child:
                      isLoading
                          ? const SizedBox(
                            width: 20,
                            height: 20,
                            child: CircularProgressIndicator(
                              strokeWidth: 2,
                              color: Colors.white,
                            ),
                          )
                          : const Text('Confirmar'),
                ),
              ],
            );
          },
        );
      },
    );
  }

  void _showRestartAppDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder:
          (context) => AlertDialog(
            title: const Text('Actualización exitosa'),
            content: const Text(
              'Los cambios se aplicarán después de reiniciar la aplicación.',
            ),
            actions: [
              TextButton(
                onPressed: () => restartApp(context),
                child: const Text('Reiniciar ahora'),
              ),
            ],
          ),
    );
  }

  Future<bool> _saveProfile(String field) async {
    // Validación específica para el campo que se está editando
    switch (field) {
      case 'nombre':
        if (_nameController.text.isEmpty) {
          throw Exception('El nombre no puede estar vacio');
        }
        break;
      case 'correo electrónico':
        if (_emailController.text.isEmpty) {
          throw Exception('El correo no puede estar vacio');
        }
        if (!RegExp(
          r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$',
        ).hasMatch(_emailController.text)) {
          throw Exception('El correo no es valido');
        }
        break;
      case 'teléfono':
        if (_phoneController.text.isEmpty) {
          throw Exception('El teléfono no puede estar vacio');
        }
        break;
      case 'contraseña':
        if (_passwordController.text.isEmpty) {
          throw Exception('La contraseña no puede estar vacía');
        }
        break;
    }

    if (!_formKey.currentState!.validate()) return false;

    setState(() => _isLoading = true);

    try {
      final userId = await obtenerUserIdDesdeToken();
      if (userId == null) throw Exception('Usuario no autenticado');

      // Crear DTO con solo el campo a actualizar
      final cliente = ClientePutDTO(
        id: userId,
        nombre: field == 'nombre' ? _nameController.text.trim() : null,
        correo:
            field == 'correo electrónico' ? _emailController.text.trim() : null,
        telefono: field == 'teléfono' ? _phoneController.text.trim() : null,
        contrasena:
            field == 'contraseña' ? _passwordController.text.trim() : null,
      );

      await _apiCliente.actualizarCliente(userId, cliente);
      return true;
    } catch (e) {
      setState(() => _mensajeError = _getUserFriendlyError(e));
      return false;
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  String _getUserFriendlyError(dynamic error) {
    if (error.toString().contains('connection')) {
      return 'Error de conexión';
    } else if (error.toString().contains('401')) {
      return 'Sesión expirada';
    }
    return error.toString();
  }
}
