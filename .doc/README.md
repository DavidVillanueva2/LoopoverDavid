# Documentación — Loopover

Documentación del proyecto siguiendo un enfoque de *Spec-Driven Development* (SDD):
primero se describe **qué** debe hacer el sistema, después **cómo** se diseña y por
último se desglosa el trabajo en tareas verificables.

## Índice

| Documento | Contenido |
|---|---|
| [constitution.md](constitution.md) | Principios y reglas que guían el desarrollo. |
| [spec.md](spec.md) | Especificación funcional: entradas, salidas, comandos y contrato de comportamiento. |
| [plan.md](plan.md) | Diseño técnico: modelo de estado, motor de búsqueda, heurísticas y PBD. |
| [tasks.md](tasks.md) | Desglose de trabajo por tareas (Tarea 1, 2 y 3) con criterios de aceptación. |
| [references.md](references.md) | Repositorios y recursos externos sobre Loopover. |

## Contexto del proyecto

`loopover` es una aplicación de línea de comandos en Java 17 que resuelve el puzzle
**Loopover 4×4** mediante búsqueda en espacio de estados con movimientos encadenados.
Usa Maven, `picocli` (CLI), `fastutil` (colecciones primitivas) y `guava`.

El estado se codifica como un `long` (bitboard de 4 bits por casilla) y las acciones
son movimientos encadenados fila/columna codificados en 5 bits.

## Estado actual

El repositorio contiene el **esqueleto** del proyecto: firmas de clases y métodos con
marcadores `TODO` para las distintas tareas de la asignatura. Consulta
[tasks.md](tasks.md) para ver el detalle de lo pendiente.
