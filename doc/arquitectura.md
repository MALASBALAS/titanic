# Arquitectura técnica

Esta sección describe la arquitectura técnica y los componentes del sistema. Se omite contexto narrativo para centrarse en la solución.

## Patrón y flujo

- Patrón: maestro–trabajador (orquestador + procesos trabajadores).
- Flujo: `ServicioEmergencia` arranca N instancias de `MainBote` → cada `MainBote` simula conteo y escribe JSON por stdout → `ServicioEmergencia` lee stdout, parsea y agrega resultados → `GeneradorInforme` persiste `Informe.md`.

## Componentes

- `ServicioEmergencia`: arranque/gestión de procesos, lectura de stdout, parseo y agregación de resultados. Manejo de errores y coordinación con `GeneradorInforme`.
- `MainBote`: proceso autónomo que calcula aleatoriamente personas/mujeres/varones/niños y emite JSON por stdout.
- `Bote`: POJO que modela los datos de un bote (personas, mujeres, varones, niños, id).
- `GeneradorInforme`: responsable de formatear y escribir `Informe.md` en Markdown; punto de extensión para otros formatos (ej. `generarHTML()` lanza UnsupportedOperationException cuando no implementado).

### Diagramas

![Arquitectura general](../doc/img/Diseño_general.jpg)

Aqui tenemos un dibujo de la visión más general del proyecto en la que servicios de emergencia le dice ha bote que cuente x 20 es decir por cada bote el número de hombre, mujeres, niños. Bote se le devuelve la información genera el markdown.

![Arquitectura especializada](../doc/img/Diseño_componentes.jpg)

En esta imagen muestro lo que contienen ambas partes del proyecto:

- Dentro de bote encontramos la clase bote que contiene los atributos de la clase como son personas, hombre, mujeres, etc y un metodo que genera de forma aleatoria un numero de personas.
- Dentro de bote hay tambien un main que ejecuta el proceso de llamar a el metodo y esperar de 2-6 segundos por cada bote y una vez terminados los 20 mandarlos a Sitemas de emergencia como se muestra en la arquitectura general.
- En la otra caja se encuentra Sistemas de emergencia que es el main que a iniciado el programa y tiene la función de ejecutar e MainBote mediante un proceso, dentro de este también se crea el informe mediante un metodo que llamado desde otra clase.
- La clase GenerarInforme es una clase que solo contiene el metodo que permite crear el Informe.md con la información que llega desde botes.

## Protocolo de comunicación

El programa se comunica de forma interna medinte llamadas directas sin ficheros ni documentos xml.

### Puntos de extensión

- Parser: hoy es un parser simple (regex); puede sustituirse por una librería JSON para mayor robustez.
- Generación de formatos: `GeneradorInforme` es el punto natural para añadir soportes adicionales (HTML, PDF), manteniendo el orquestador sin cambios.

### Errores y tolerancia

El orquestador captura errores al iniciar procesos y errores de lectura; los procesos fallidos quedan registrados y no impiden que se genere el informe con los datos disponibles.

Este diseño separa responsabilidades claras (orquestación, simulación y generación de informe) y facilita el testing unitario y la sustitución de componentes.
