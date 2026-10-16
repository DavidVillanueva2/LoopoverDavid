# 01. CONTEXTO

## ¿Qué es Loopover?

Loopover es un puzzle toroidal de 4x4 sin huecos, con movimientos engranados:
al mover una fila, también se mueve una columna (el punto de intersección participa
en ambos desplazamientos). La versión que se modela aquí es la "Loopover engranado 4x4".

El objetivo del proyecto es implementar un resolvedor/inspector por CLI para este
puzzle, siguiendo una asignación por tareas (Tareas 1–3).

## Estado actual

- Base del proyecto: `pom.xml` + clases con stubs (`TODO: Tarea 2/3`)
- Tarea 1 completada: **únicamente `src/Estado.java`** ha sido implementado.
  Las demás clases del `src/` permanecen tal como estaban en el proyecto base.
- Se mantiene `test/TestEstado.java` como test auxiliar local (no enlazado con Maven).
- `.gitignore` ajustado para ignorar artefactos locales.

## Tarea 1

Implementar la representación del tablero y las transiciones:

- Estado como **bitboard** (4 bits por casilla) en un `long`.
- 32 acciones codificadas en 5 bits (`fila(2) | col(2) << 2 | signo(1) << 4`).
- Desplazamiento circular toroidal: `+` = fila derecha + columna abajo; `-` = fila izquierda + columna arriba.
- Orden: **primero fila, después columna** (movimiento engranado).
- API: constructores (`long`, `String`, `int[]`), `bitboard()`, `ficha(int)`, `ficha(int,int)`, `esResuelto()`, `sucesores()`, `aplicar(int)`, `accionComoTexto(int)`, `accionDesde(String)`, `equals/hashCode/toString`.
