# Referencias — Loopover

Recopilación de repositorios, implementaciones y recursos externos sobre el puzzle
**Loopover** (creado originalmente por carykh).

## Origen y lógica del juego

| Recurso | Descripción |
|---|---|
| [carykh (GitHub)](https://github.com/carykh) | Autor original del puzzle Loopover. |
| [janispritzkau/loopover](https://github.com/janispritzkau/loopover) | Implementación web con lógica de juego de alto nivel (temporizador, eventos, etc.). |
| [typicel/loopover-ios](https://github.com/typicel/loopover-ios) | Versión para iOS del juego. |
| [loopover.xyz](https://loopover.xyz/) | Juego original jugable online (con soporte offline). |

## Solvers y búsqueda

| Recurso | Descripción |
|---|---|
| [coolcomputery/Loopover-Brute-Force-and-Improvement](https://github.com/coolcomputery/Loopover-Brute-Force-and-Improvement) | Solver en C++ con enfoque de construcción por bloques que explota las simetrías del puzzle. |
| [torchlight/loopsolver](https://github.com/torchlight/loopsolver) | Solver específico para las variantes 4×4 y 5×5. |
| [JonathanAlderson/LoopOver](https://github.com/JonathanAlderson/LoopOver) | Solver en Python; genera la secuencia de movimientos casi al instante. |
| [AndreiToroplean/loopover](https://github.com/AndreiToroplean/loopover) | Implementación del puzzle junto a un algoritmo de resolución (kata de Codewars). |
| [BonnyAD9/Loopover](https://github.com/BonnyAD9/Loopover) | Remake para consola en C#/.NET con estadísticas persistentes. |

## Teoría: número de Dios y cotas

| Recurso | Descripción |
|---|---|
| [coolcomputery/Loopover-Multi-Phase-God-s-Number](https://github.com/coolcomputery/Loopover-Multi-Phase-God-s-Number) | Calcula cotas superiores del "God's number" extendiendo rectángulos resueltos. |
| [Speedsolving — Loopover God's number upper bounds](https://www.speedsolving.com/threads/loopover-gods-number-upper-bounds-4%C3%974-asymptotics-etc.75180/) | Hilo técnico sobre cotas óptimas y solvers de dos fases. |

## Enunciado / katas

| Recurso | Descripción |
|---|---|
| [Codewars — Loopover kata](https://www.codewars.com/kata/5c1d796370fee68b1e000611) | Enunciado del kata de Loopover (incluye discusión en Java). |

## Notas para este proyecto

- El enfoque de este repositorio es una **búsqueda en espacio de estados** con
  movimientos encadenados (fila + columna simultáneos), a diferencia de los solvers
  por fases/bloques de `coolcomputery`.
- Para el diseño de heurísticas admisibles (PBD de 8 piezas) resultan útiles las ideas
  de partición de piezas de las PDB clásicas aplicadas al 15-puzzle.
