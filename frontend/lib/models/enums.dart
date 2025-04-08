enum UserType { CUSTOMER, STAFF }

enum StaffType { ADMIN, RECEPTIONIST, INSTRUCTOR }

enum DayOfWeek {
  monday(1),
  tuesday(2),
  wednesday(3),
  thursday(4),
  friday(5),
  saturday(6),
  sunday(7);

  final int value;

  const DayOfWeek(this.value);

  // Method to get the DayOfWeek from an integer value
  static DayOfWeek fromValue(int value) {
    return DayOfWeek.values.firstWhere(
      (e) => e.value == value,
      orElse: () => throw ArgumentError("Invalid day value: $value"),
    );
  }

  // Method to get the DayOfWeek from the name (in English)
  static DayOfWeek fromName(String name) {
    switch (name.toUpperCase()) {
      case 'MONDAY':
        return DayOfWeek.monday;
      case 'TUESDAY':
        return DayOfWeek.tuesday;
      case 'WEDNESDAY':
        return DayOfWeek.wednesday;
      case 'THURSDAY':
        return DayOfWeek.thursday;
      case 'FRIDAY':
        return DayOfWeek.friday;
      case 'SATURDAY':
        return DayOfWeek.saturday;
      case 'SUNDAY':
        return DayOfWeek.sunday;
      default:
        throw ArgumentError("Invalid day name: $name");
    }
  }
}
