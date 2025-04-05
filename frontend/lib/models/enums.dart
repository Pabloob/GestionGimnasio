enum TipoUsuario { CLIENTE, TRABAJADOR }

enum TipoTrabajador { ADMIN, RECEPCIONISTA, INSTRUCTOR }

enum DiaSemana {
  lunes(1),
  martes(2),
  miercoles(3),
  jueves(4),
  viernes(5),
  sabado(6),
  domingo(7);

  final int value;
  const DiaSemana(this.value);

  static DiaSemana fromValue(int value) {
    return DiaSemana.values.firstWhere(
      (e) => e.value == value,
      orElse: () => throw ArgumentError("Valor de día inválido: $value"),
    );
  }

  static DiaSemana fromName(String name) {
  switch (name.toUpperCase()) {
    case 'MONDAY':
      return DiaSemana.lunes;
    case 'TUESDAY':
      return DiaSemana.martes;
    case 'WEDNESDAY':
      return DiaSemana.miercoles;
    case 'THURSDAY':
      return DiaSemana.jueves;
    case 'FRIDAY':
      return DiaSemana.viernes;
    case 'SATURDAY':
      return DiaSemana.sabado;
    case 'SUNDAY':
      return DiaSemana.domingo;
    default:
      throw ArgumentError("Día inválido: $name");
  }
}

}
