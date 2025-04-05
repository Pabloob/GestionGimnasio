import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frontend/pages/components/clase_card.dart';
import 'package:frontend/providers/cliente_providers.dart';

import '../../providers/common_providers.dart';
import '../components/common_widgets.dart';

class ClientHomePage extends ConsumerWidget {
  const ClientHomePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final userAsync = ref.watch(userProvider);
    final classesAsync = ref.watch(clienteClasesProvider);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (error, _) => Center(child: Text("Error: $error")),
      data:
          (user) => SingleChildScrollView(
            child: Column(
              children: [
                CommonWidgets.buildCustomTopMesage(
                  user: user.usuario,
                  textoPrincipal: "Bienvenido",
                  textoSecundario: "¿Listo para entrenar?",
                ),
                Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        "Tus clases",
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 10),
                      classesAsync.when(
                        loading:
                            () => const Center(
                              child: CircularProgressIndicator(),
                            ),
                        error:
                            (error, _) => Center(child: Text('Error: $error')),
                        data:
                            (classes) =>
                                classes.isEmpty
                                    ? const Center(
                                      child: Text(
                                        'No estás inscrito en ninguna clase.',
                                      ),
                                    )
                                    : ListView.builder(
                                      shrinkWrap: true,
                                      physics:
                                          const NeverScrollableScrollPhysics(),
                                      itemCount: classes.length,
                                      itemBuilder:
                                          (context, index) => ClassCard(
                                            clase: classes[index].clase,
                                            isEnrolled: true,
                                            ref: ref,
                                          ),
                                    ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
    );
  }
}
