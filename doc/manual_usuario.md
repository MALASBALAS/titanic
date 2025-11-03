# Manual de usuario

Este documento explica cómo compilar y ejecutar el simulador (`ServicioEmergencia`) y cómo ejecutar los tests del proyecto.

## Requisitos

- Java 8+ (se recomienda Java 17)
- Maven 3.6+

Verifica tu versión de Java con:

```bash
java -version
```

## Compilar el proyecto

1. Desde la raíz del repositorio, ejecuta:

```bash
mvn package
```

Esto compilará las clases y dejará los artefactos en `target/`.

Si prefieres compilar sin ejecutar tests de integración o unitarios, añade `-DskipTests`:

```bash
mvn package -DskipTests
```

## Ejecutar el simulador (`ServicioEmergencia`)

Tras compilar, lanza el orquestador mediante Java usando el classpath de `target/classes`:

```bash
java -cp target/classes com.alvaroyraul.ServicioEmergencia
```

Comportamiento esperado:

- El programa inicia 20 procesos `MainBote` (B00..B19).
- Cada proceso escribe un JSON por stdout con su conteo.
- `ServicioEmergencia` leerá todos los JSON, los agregará y generará el fichero `Informe.md` en la raíz del proyecto.

Salida clave:

- `Informe.md` — informe en Markdown con detalle por bote y totales.

Ejemplo de comprobación rápida (en logs):

```

Iniciando ServicioEmergencia: se lanzarán 20 procesos...
Recibido: B00
...
Recibido: B19
Informe.md generado con éxito.
```

## Ejecutar los tests

Para ejecutar la suite completa de tests unitarios:

```bash
mvn test
```

Para ejecutar solamente la prueba que valida el informe y la ejecución de los 20 botes:

```bash
mvn -Dtest=PruebasTitanicTest test
```

Resultados y artefactos de pruebas:

- `target/surefire-reports` — resultados de los tests (XML/texto).
- `Informe.md` — generado al ejecutar el orquestador desde la aplicación.

## Contacto

Para dudas sobre el uso o para reportar un bug, abre un issue o contacte con nosotros.
