import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

import '../../theme/app_theme.dart';
import '../../utils/authService.dart';
import 'admin_add.dart';
import 'admin_home.dart';
import 'admin_profile.dart';

class AdminMainScreen extends StatefulWidget {
  const AdminMainScreen({super.key});

  @override
  State<AdminMainScreen> createState() => _AdminMainScreenState();
}

class _AdminMainScreenState extends State<AdminMainScreen> {
  int _selectedIndex = 0;
  DateTime? _lastBackButtonPressTime;

  final List<Widget> _pages = const [
    AdminHomePage(key: ValueKey('homePage')),
    AdminAddPage(key: ValueKey('addClassPage')),
    AdminProfilePage(key: ValueKey('profilePage')),
  ];

  static const List<String> _titles = ["Inicio", "Añadir", "Perfil"];

  static const List<GButton> _navButtons = [
    GButton(icon: Icons.home, text: 'Inicio'),
    GButton(icon: Icons.add, text: 'Añadir'),
    GButton(icon: Icons.person, text: 'Perfil'),
  ];

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvoked: (didPop) => _handleBackButton(context, didPop),
      child: Scaffold(
        appBar: AppBar(
          title: _buildAppBar(),
          backgroundColor: AppTheme.backgroundColor,
          elevation: 0,
          centerTitle: true,
        ),
        backgroundColor: AppTheme.backgroundColor,
        body: IndexedStack(index: _selectedIndex, children: _pages),
        bottomNavigationBar: _buildBottomBar(),
      ),
    );
  }

  void _handleBackButton(BuildContext context, bool didPop) {
    if (didPop) return;

    final currentTime = DateTime.now();
    final shouldShowSnackbar = _lastBackButtonPressTime == null ||
        currentTime.difference(_lastBackButtonPressTime!) > const Duration(seconds: 2);

    if (shouldShowSnackbar) {
      _lastBackButtonPressTime = currentTime;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Presiona de nuevo para salir'),
          duration: Duration(seconds: 2),
          behavior: SnackBarBehavior.floating,
        ),
      );
    } else {
      AuthService().logout(context);
    }
  }

  Widget _buildAppBar() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image.asset("assets/icons/icon.png", height: 40),
        const SizedBox(width: 12),
        Text(
          _titles[_selectedIndex],
          style: AppTheme.appBarTitleStyle,
        ),
      ],
    );
  }

  Widget _buildBottomBar() {
    return Container(
      decoration: BoxDecoration(
        color: Colors.black,
        boxShadow: [
          BoxShadow(
            blurRadius: 20,
            color: Colors.black.withOpacity(0.1),
          )
        ],
      ),
      child: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 12),
          child: GNav(
            backgroundColor: Colors.black,
            color: Colors.white,
            activeColor: Colors.white,
            tabBackgroundColor: AppTheme.primaryColor.withOpacity(0.7),
            rippleColor: AppTheme.primaryColor,
            gap: 8,
            onTabChange: (index) => setState(() => _selectedIndex = index),
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
            tabs: _navButtons,
            selectedIndex: _selectedIndex,
          ),
        ),
      ),
    );
  }
}