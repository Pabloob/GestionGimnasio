import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/pages/about_pages/about_app.dart';
import 'package:frontend/pages/about_pages/help_and_support.dart';
import 'package:frontend/pages/client_pages/client_edit_profile.dart';
import 'package:frontend/providers/common_providers.dart';

import '../../utils/authService.dart';
import '../components/common_widgets.dart';

class ClientProfilePage extends ConsumerWidget {
  const ClientProfilePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final userAsync = ref.watch(userProvider);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (error, _) => Center(child: Text('Error: $error')),
      data:
          (user) => SingleChildScrollView(
        child: Column(
          children: [
            CommonWidgets.buildCustomTopMesage(user: user.user),
            _buildProfileOptions(context),
            _buildMoreOptions(context),
          ],
        ),
      ),
    );
  }

  Widget _buildProfileOptions(BuildContext context) {
    return Column(
      children: [
        ListTile(
          leading: const Icon(Icons.person),
          title: const Text('Mi cuenta'),
          subtitle: const Text('Edita tu información personal'),
          trailing: const Icon(Icons.arrow_forward_ios),
          onTap:
              () => Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => const ClientEditProfile(),
            ),
          ),
        ),
        const Divider(),
        ListTile(
          leading: const Icon(Icons.logout),
          title: const Text('Cerrar sesión'),
          onTap: () => AuthService().logout(context),
        ),
        const Divider(),
      ],
    );
  }

  Widget _buildMoreOptions(BuildContext context) {
    return Column(
      children: [
        ListTile(
          leading: const Icon(Icons.help),
          title: const Text('Ayuda y soporte'),
          onTap:
              () => Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => const HelpAndSupportPage(),
            ),
          ),
        ),
        const Divider(),
        ListTile(
          leading: const Icon(Icons.info),
          title: const Text('Acerca de la app'),
          onTap:
              () => Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const AboutAppPage()),
          ),
        ),
      ],
    );
  }
}