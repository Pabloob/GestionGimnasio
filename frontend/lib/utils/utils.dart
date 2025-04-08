String getErrorMessage(dynamic error) {
  if (error.toString().contains('socket') ||
      error.toString().contains('connection')) {
    return 'Error de conexión. Verifica tu internet';
  } else if (error.toString().contains('401')) {
    return 'Correo o contraseña incorrectos';
  }
  return 'Error al iniciar sesión. Intenta nuevamente';
}
