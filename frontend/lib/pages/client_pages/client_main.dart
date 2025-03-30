import 'package:flutter/material.dart';
import 'package:frontend/apis/api_service.dart';
import 'package:frontend/apis/api_usuario.dart';
import 'package:frontend/utils/utils.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'client_home.dart';
import 'cliente_add.dart';
import 'cliente_profile.dart';

class ClientMainScreen extends StatefulWidget {
  const ClientMainScreen({super.key});

  @override
  State<ClientMainScreen> createState() => _ClientMainScreenState();
}

class _ClientMainScreenState extends State<ClientMainScreen> {
  int _selectedIndex = 0;
  int _reloadCounter = 0;
  DateTime? _lastBackButtonPressTime;
  ApiUsuario apiUsuario = ApiUsuario(apiService: ApiService());
  List<Widget> get _pages => [
    ClientHomePage(key: ValueKey('home$_reloadCounter')),
    ClienteAddPage(
      key: ValueKey('add$_reloadCounter'),
      onEnrollmentSuccess: _reloadAllPages,
    ),
    const ClienteProfilePage(),
  ];

  static const List<String> _titles = ["Tus clases", "Clases", "Perfil"];
  static const List<GButton> _navButtons = [
    GButton(icon: Icons.home, text: 'Home'),
    GButton(icon: Icons.add_box, text: 'Add'),
    GButton(icon: Icons.account_box, text: 'Profile'),
  ];

  void _reloadAllPages() {
    setState(() {
      _reloadCounter++;
    });
  }

  void _handlePopInvokedWithResult(bool didPop, dynamic result) {
    if (!didPop) {
      final currentTime = DateTime.now();
      final backButtonHasNotBeenPressedOrSnackbarHasBeenClosed =
          _lastBackButtonPressTime == null ||
          currentTime.difference(_lastBackButtonPressTime!) >
              Duration(seconds: 2);

      if (backButtonHasNotBeenPressedOrSnackbarHasBeenClosed) {
        _lastBackButtonPressTime = currentTime;
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Presiona de nuevo para salir'),
            duration: Duration(seconds: 2),
          ),
        );
      } else {
        restartApp(context);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvokedWithResult: _handlePopInvokedWithResult,
      child: Scaffold(
        appBar: AppBar(
          title: _buildAppBar(),
          backgroundColor: Colors.transparent,
          elevation: 0,
        ),
        backgroundColor: const Color(0xffF5F5F5),
        body: IndexedStack(index: _selectedIndex, children: _pages),
        bottomNavigationBar: _buildBottomBar(),
      ),
    );
  }

  Widget _buildAppBar() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const SizedBox(width: 15),
        Image.asset("assets/icons/icon.png", height: 40),
        const SizedBox(width: 25),
        Expanded(
          child: Text(
            _titles[_selectedIndex],
            style: const TextStyle(
              fontSize: 25,
              fontWeight: FontWeight.bold,
              color: Color(0xfffa6045),
            ),
            overflow: TextOverflow.ellipsis,
            maxLines: 1,
          ),
        ),
      ],
    );
  }

  Widget _buildBottomBar() {
    return Container(
      color: Colors.black,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 20),
        child: GNav(
          backgroundColor: Colors.black,
          color: Colors.white,
          activeColor: Colors.white,
          tabBackgroundColor: Colors.grey.shade800,
          rippleColor: const Color(0xfffa6045),
          gap: 8,
          onTabChange: (index) => setState(() => _selectedIndex = index),
          padding: const EdgeInsets.all(16),
          tabs: _navButtons,
          selectedIndex: _selectedIndex,
        ),
      ),
    );
  }
}
