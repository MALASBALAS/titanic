# Problemas encontrados

Durante el desarrollo se encontraron los siguientes inconvenientes:

- Dificultad inicial para lanzar procesos con `ProcessBuilder` correctamente configurado, posteriormente ya entendimos el funcionamiento correcto.
- Problemas de sincronización al recoger la salida de los procesos; se solucionó usando `waitFor()`.
- Ajustes en las expresiones regulares para extraer correctamente los valores numéricos del JSON.
- Problemas con testeos de JUNIT inicialmente.

## Elementos destacables en el desarrollo

- Al principio nos costo entender la logica de como realizar el programa, empezaos a programar sin diseño, cosa que despues ocasiono problemas con el diseño porque queriamos realizar el diseño en base a lo que teniamos, y empezamos desde el principio pero empezando con el diseño, cosa que posteriormente nos facilito el proceso y acabamos ganando tiempo en varios apartados.
- Hemos querido perfeccionar todo lo posible varios apartados, como intentar crear más constantes de las planeadas al principio para su futura modificacion si fuese necesaria.
- Al tener poco tiempo entre nosotros, por choque de horarios, hemos tenido que cuadrarnos bien para poder realizar el trabajo en equipo, cosa que el diseño nos facilito el reparto de tareas.
