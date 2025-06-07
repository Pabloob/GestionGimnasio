import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../providers/common_providers.dart';
import '../../theme/app_theme.dart';
import '../../utils/authService.dart';
import '../about_pages/about_app.dart';
import '../about_pages/help_and_support.dart';
import '../components/common_widgets.dart';

class AdminProfilePage extends ConsumerWidget {
  const AdminProfilePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final userAsync = ref.watch(userProvider);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error:
          (error, _) =>
              Center(child: Text('Error: $error', style: AppTheme.errorText)),
      data:
          (user) => Scaffold(
            backgroundColor: AppTheme.backgroundColor,
            body: SingleChildScrollView(
              child: Column(
                children: [
                  CommonWidgets.buildCustomTopMesage(user: user.user),
                  _buildProfileOptions(context),
                  _buildMoreOptions(context),
                ],
              ),
            ),
          ),
    );
  }

  Widget _buildProfileOptions(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
      ),
      child: Column(
        children: [
          ListTile(
            leading: Icon(Icons.logout, color: AppTheme.primaryColor),
            title: const Text('Cerrar sesión'),
            onTap: () => AuthService().logout(context),
          ),
        ],
      ),
    );
  }

  Widget _buildMoreOptions(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppTheme.defaultRadius),
      ),
      child: Column(
        children: [
          ListTile(
            leading: Icon(Icons.help, color: AppTheme.primaryColor),
            title: const Text('Ayuda y soporte'),
            trailing: const Icon(Icons.arrow_forward_ios, size: 16),
            onTap: () => Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const HelpAndSupportPage(),
              ),
            ),
          ),
          const Divider(height: 1),
          ListTile(
            leading: Icon(Icons.info, color: AppTheme.primaryColor),
            title: const Text('Acerca de la app'),
            trailing: const Icon(Icons.arrow_forward_ios, size: 16),
            onTap: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => const AboutAppPage()),
            ),
          ),
        ],
      ),
    );
  }
}
