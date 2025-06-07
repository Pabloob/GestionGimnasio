import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../models/get/FitnessClassGetDTO.dart';
import '../../models/post/EnrollmentPostDTO.dart';
import '../../models/post/PaymentPostDTO.dart';
import '../../providers/cliente_providers.dart';
import '../../providers/common_providers.dart';
import '../components/add_clase_card.dart';
import '../components/common_widgets.dart';

class ClientAddClassPage extends ConsumerWidget {
  final VoidCallback? onEnrollmentSuccess;

  const ClientAddClassPage({super.key, this.onEnrollmentSuccess});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final userAsync = ref.watch(userProvider);
    final availableClassesAsync = ref.watch(clienteAvaibleClasesProvider);
    final selectedClasses = ref.watch(clienteSelectedClasesProvider);

    return userAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (error, _) => Center(child: Text("Error: $error")),
      data:
          (user) => SingleChildScrollView(
            child: Column(
              children: [
                CommonWidgets.buildCustomTopMesage(
                  user: user.user,
                  textoPrincipal: "Clases disponibles",
                  textoSecundario: "Inscríbete a nuevas clases",
                ),
                if (selectedClasses.isNotEmpty)
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                    child: Align(
                      alignment: Alignment.centerRight,
                      child: ElevatedButton(
                        onPressed: () => _showEnrollDialog(context, ref),
                        child: const Text("Confirmar inscripción"),
                      ),
                    ),
                  ),
                Padding(
                  padding: const EdgeInsets.all(16),
                  child: availableClassesAsync.when(
                    loading:
                        () => const Center(child: CircularProgressIndicator()),
                    error: (error, _) => Center(child: Text('Error: $error')),
                    data:
                        (classes) =>
                            classes.isEmpty
                                ? const Center(
                                  child: Text('No hay clases disponibles.'),
                                )
                                : ListView.builder(
                                  shrinkWrap: true,
                                  physics: const NeverScrollableScrollPhysics(),
                                  itemCount: classes.length,
                                  itemBuilder:
                                      (context, index) => AddClaseCard(
                                        clase: classes[index],
                                        isSelected: selectedClasses.contains(
                                          classes[index],
                                        ),
                                        onToggle:
                                            () => _toggleClassSelection(
                                              ref,
                                              classes[index],
                                            ),
                                      ),
                                ),
                  ),
                ),
              ],
            ),
          ),
    );
  }

  void _toggleClassSelection(WidgetRef ref, FitnessClassGetDTO clase) {
    ref.read(clienteSelectedClasesProvider.notifier).update((state) {
      return state.contains(clase)
          ? state.where((c) => c != clase).toList()
          : [...state, clase];
    });
  }

  void _showEnrollDialog(BuildContext context, WidgetRef ref) {
    final selectedClasses = ref.read(clienteSelectedClasesProvider);

    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            title: const Text("Confirmar Inscripción"),
            content: SingleChildScrollView(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Text("Las siguientes clases serán reservadas:"),
                  const SizedBox(height: 16),
                  ...selectedClasses.map(
                    (clase) => ListTile(
                      title: Text(clase.name),
                      trailing: Text("${clase.price} €"),
                    ),
                  ),
                  const Divider(),
                  ListTile(
                    title: const Text(
                      "Total",
                      style: TextStyle(fontWeight: FontWeight.bold),
                    ),
                    trailing: Text(
                      "${selectedClasses.fold(0.0, (sum, c) => sum + (c.price))} €",
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                  ),
                ],
              ),
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: const Text("Cancelar"),
              ),
              TextButton(
                onPressed: () async {
                  await _handleEnrollment(context, ref);
                },
                child: const Text("Confirmar"),
              ),
            ],
          ),
    );
  }

  Future<void> _handleEnrollment(BuildContext context, WidgetRef ref) async {
    final selectedClasses = ref.read(clienteSelectedClasesProvider);

    final userId = (await ref.read(userProvider.future)).user.id;

    try {
      double totalPrice = 0.0;
      for (final clase in selectedClasses) {
        totalPrice += clase.price;
      }

      for (final clase in selectedClasses) {
        await ref
            .read(inscripcionServiceProvider)
            .createEnrollment(
              EnrollmentPostDTO(
                customerId: userId,
                classId: clase.id,
                attended: false,
              ),
            );
      }

      await ref
          .read(pagoServiceProvider)
          .createPayment(
            PaymentPostDTO(customerId: userId, amount: totalPrice, paid: false),
          );

      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Inscripción completada con éxito'),
            backgroundColor: Colors.green,
          ),
        );

        // Resetear selección y recargar clases
        ref.read(clienteSelectedClasesProvider.notifier).state = [];
        ref.invalidate(clienteAvaibleClasesProvider);
        ref.invalidate(clienteClasesProvider);

        onEnrollmentSuccess?.call();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Horario añadido con éxito'),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error al inscribirse: ${e.toString()}'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }
}
