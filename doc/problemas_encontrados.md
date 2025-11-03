# Problemas encontrados

Durante el desarrollo se encontraron los siguientes inconvenientes:

- Dificultad inicial para lanzar procesos con `ProcessBuilder` correctamente configurado, posteriormente ya entendimos el funcionamiento correcto.
- Problemas de sincronización al recoger la salida de los procesos; se solucionó usando `waitFor()`.
- Ajustes en las expresiones regulares para extraer correctamente los valores numéricos del JSON.
- Prolemas con testeos de JUNIT inicialmente.
