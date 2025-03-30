import 'package:flutter/material.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/pages/about_pages/about_app.dart';
import 'package:frontend/pages/about_pages/help_and_support.dart';
import 'package:frontend/pages/client_pages/client_edit_profile.dart';
import 'package:frontend/utils/common_widgets.dart';
import 'package:frontend/utils/utils.dart';

class ClienteProfilePage extends StatefulWidget {
  const ClienteProfilePage({super.key});

  @override
  State<ClienteProfilePage> createState() => _ClienteProfilePageState();
}

class _ClienteProfilePageState extends State<ClienteProfilePage> {
  dynamic _user;
  ApiUsuario apiUsuario = ApiUsuario(apiService: ApiService());

  @override
  void initState() {
    super.initState();
    _loadUser();
  }

  Future<void> _loadUser() async {
    final usuarioGuardado = await obtenerUsuarioGuardado();
    if (mounted) {
      setState(() => _user = usuarioGuardado);
    }
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          CommonWidgets.buildCustomTopMesage(
            user: _user,
            avatar: CircleAvatar(radius: 20),
          ),

          // Sección principal
          Padding(
            padding: EdgeInsets.all(16),
            child: Column(
              children: [
                // My Account
                ListTile(
                  leading: Icon(Icons.person),
                  title: Text('My Account'),
                  subtitle: Text('Make changes to your account'),
                  trailing: Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const ClientEditProfile(),
                      ),
                    );
                  },
                ),
                Divider(),

                // Two-Factor Authentication
                ListTile(
                  leading: Icon(Icons.shield),
                  title: Text('Two-Factor Authentication'),
                  subtitle: Text('Further secure your account for safety'),
                  trailing: Icon(Icons.arrow_forward_ios),
                  onTap: () {},
                ),
                Divider(),

                // Log Out
                ListTile(
                  leading: Icon(Icons.logout),
                  title: Text('Log out'),
                  subtitle: Text('Further secure your account for safety'),
                  trailing: Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    restartApp(context);
                  },
                ),
                Divider(),
              ],
            ),
          ),

          // Sección "More"
          Padding(
            padding: EdgeInsets.all(16),
            child: Column(
              children: [
                Text(
                  'More',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 16),

                // Help & Support
                ListTile(
                  leading: Icon(Icons.help),
                  title: Text('Help & Support'),
                  trailing: Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const HelpAndSupportPage(),
                      ),
                    );
                  }, // OnClickListener
                ),
                Divider(),

                // About App
                ListTile(
                  leading: Icon(Icons.info),
                  title: Text('About App'),
                  trailing: Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const AboutAppPage(),
                      ),
                    );
                  }, // OnClickListener
                ),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
