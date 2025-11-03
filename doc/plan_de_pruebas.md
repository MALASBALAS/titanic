# Plan de pruebas

- Prueba 1: Ejecutar `ServicioEmergencia` → genera correctamente `Informe.md` con los 20 botes y totales coherentes ✅
- Prueba 2: Simular error en un proceso (forzar excepción en `MainBote`) → se muestra `MSG_ERROR` y el programa continúa ✅
- Prueba 3: El informe contiene fecha y hora de ejecución correctas ✅
- Prueba 4: Validar mediante JUnit (`PruebasTitanicTest.java`) que el archivo `Informe.md` existe y contiene la etiqueta `# SERVICIO DE EMERGENCIAS` ✅
