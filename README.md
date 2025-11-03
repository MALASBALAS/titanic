
# Titanic — simulador de orquestación de botes (Java)

![Build](https://img.shields.io/badge/build-passing-brightgreen) ![Java](https://img.shields.io/badge/java-17-blue)

## Índice

- [Titanic — simulador de orquestación de botes (Java)](#titanic--simulador-de-orquestación-de-botes-java)
  - [Índice](#índice)
  - [Descripción del proyecto](#descripción-del-proyecto)
  - [Estructura del repositorio](#estructura-del-repositorio)
  - [Inicio rápido](#inicio-rápido)
  - [Salida (Informe)](#salida-informe)
  - [Uso avanzado y CI](#uso-avanzado-y-ci)
  - [Estado actual y pruebas](#estado-actual-y-pruebas)
  - [Cómo contribuir](#cómo-contribuir)
  - [Autores y licencia](#autores-y-licencia)
  - [Documentación técnica](#documentación-técnica)
  - [Anexos](#anexos)

## Descripción del proyecto

Proyecto académico que simula la gestión de 20 botes salvavidas mediante procesos Java independientes. Cada bote (proceso `MainBote`) emite por stdout un JSON con el conteo de personas; `ServicioEmergencia` orquesta los procesos, agrega resultados y persiste un informe en `Informe.md`.

## Estructura del repositorio

- `src/main/java/com/alvaroyraul/` — código fuente (orquestador, POJOs, generador de informe)
- `src/test/java/com/alvaroyraul/PruebasTitanicTest.java` — tests unitarios
- `doc/` — documentación técnica y material de entrega (leer `doc/index.md`)

## Inicio rápido

Requisitos: Java 8+ (Java 17 recomendado) y Maven.

1. Compilar

```bash
mvn package
```

2. Ejecutar (modo local)

```bash
java -cp target/classes com.alvaroyraul.ServicioEmergencia
```

1. Ejecutar pruebas

```bash
mvn test
```

## Salida (Informe)

Al finalizar la ejecución se genera `Informe.md` en la raíz del proyecto con el detalle por bote y los totales agregados.

## Uso avanzado y CI

- Para CI/runner: ejecute Maven en el JDK del runner y archive `target/surefire-reports` y `Informe.md` como evidencia.
- Extensiones: `GeneradorInforme` contiene el stub `generarHTML()` como punto de extensión para futuros formatos.

## Estado actual y pruebas

- Código probado: la suite `PruebasTitanicTest` valida el formato del informe y que se lancen 20 procesos (tests verdes en esta máquina).
- Robustez: el parser actual es simple (regex). Recomendable migrar a una biblioteca JSON (Gson/Jackson) si se requiere mayor tolerancia.

## Cómo contribuir

1. Clona el repositorio
2. Crea una rama `feature/xxx`
3. Añade tests para cualquier cambio funcional
4. Abre un pull request

## Autores y licencia

Autores: Álvaro Balas y Raúl Sánchez

## Documentación técnica

Ver `doc/indice.md` para el índice de la documentación: arquitectura, plan de pruebas, problemas encontrados y conclusiones.

## Anexos

Github de Alvaro: [MALASBALAS_Titanic](https://github.com/MALASBALAS/titanic)
Github de Raul: [Raulsr19_Titanic](https://github.com/Raulsr19/Titanic)
