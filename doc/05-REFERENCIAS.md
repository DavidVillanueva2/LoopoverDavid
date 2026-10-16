# 05. REFERENCIAS

## Guía de modelado

- `loopover_engranado_4x4.md` (local) — describe la variante engranada 4x4,
  codificación, ejemplo `02+` y semántica (fila primero, columna después).
  Se encuentra ignorado en git (`/.gitignore`) pero presente localmente.

## Código base

- `src/Estado.java` — única clase modificada respecto al proyecto base.
- `src/Sucesor.java` — tupla `(accion, estado, costo)` (ya implementada).
- `src/ComandoVerificar.java` / `LoopoverApp.java` — CLI con picocli (ya implementados).
- Resto (`Busqueda`, `Nodo`, `Frontera`, `Visitados`, `Heuristicas`, `HeuristicasPBD`, `PBD`, `ComandoBusqueda`) — stubs de Tareas 2–3.

## Verificación externa

- Ejemplo guía: `verify -s 00010203040506070809101112131415 -a "02+"` → `03001402040501070809061112131015`
- Movimiento engranado: fila circular + columna circular (misma fila/columna que indica la acción)
- Inmutabilidad: `aplicar` devuelve nuevo `Estado`

## Dependencias (pom.xml)

- `picocli 4.7.7`
- `fastutil 8.5.16`
- `guava 33.7.1-jre`
