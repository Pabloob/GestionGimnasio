import 'package:flutter/material.dart';

class BottomNavigationBarCustom extends StatelessWidget {
  final String textButton1;
  final String textButton2;
  final VoidCallback onClick1;
  final VoidCallback onClick2;

  const BottomNavigationBarCustom({
    super.key,
    required this.textButton1,
    required this.textButton2,
    required this.onClick1,
    required this.onClick2,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 88, right: 24, left: 24),
      child: Row(
        children: [
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick1,
                style: ElevatedButton.styleFrom(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  backgroundColor: const Color(0xfffa6045),
                  shadowColor: Colors.black26,
                ),
                child: Text(
                  textButton1,
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ),
          const SizedBox(width: 20),
          Expanded(
            child: SizedBox(
              height: 55,
              child: ElevatedButton(
                onPressed: onClick2,
                style: ElevatedButton.styleFrom(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  backgroundColor: const Color(0XFF939393),
                  shadowColor: Colors.black26,
                ),
                child: Text(
                  textButton2,
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}