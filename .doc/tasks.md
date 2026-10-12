# Tareas

Desglose del trabajo pendiente. El esqueleto del repositorio marca los puntos a
implementar con comentarios `TODO` (por ejemplo `TODO: Tarea 1`,
`throw new UnsupportedOperationException("TODO: Tarea 2")`).

## Tarea 1 — Representación del estado (`Estado`)

**Objetivo:** implementar el bitboard, los constructores, las consultas, la aplicación
de acciones y las utilidades de codificación.

- [ ] `Estado(long bitboard)`: almacenar el bitboard.
- [ ] `Estado(String)`: validar longitud (32 dígitos), parsear 16 pares, construir.
- [ ] `Estado(int[])`: validar longitud y dominio, construir bitboard.
- [ ] `construirBitboard(int[])`: comprobar sin duplicados y rango `[0, 16)`.
- [ ] `bitboard()`, `ficha(int)`, `ficha(int,int)`, `esResuelto()`.
- [ ] `sucesores()`: 32 `Sucesor` con costo `1.0f`.
- [ ] `aplicar(int)`: `desplazarFila` + `desplazarColumna` según signo.
- [ ] `desplazarFila(...)`: rotación circular de 16 bits.
- [ ] `desplazarColumna(...)`: rotación circular de 4 nibbles.
- [ ] `accionComoTexto(int)` / `accionDesde(String)`.
- [ ] `equals`, `hashCode`, `toString` (32 dígitos).

**Criterio de aceptación:** `verify -s <resuelto>` lista 32 sucesores coherentes y
`verify -s <estado> -a <acciones>` aplica las acciones correctamente.

## Tarea 2 — Motor de búsqueda

**Clases:** `Nodo`, `Frontera`, `Visitados`, `Busqueda`.

- [ ] `Nodo`: constructores y accesores (estado, padre, acción, profundidad,
      costo acumulado, heurística, valor).
- [ ] `Nodo`: contabilidad para SMA\* (`restarHijo`, `reexpandir`, `marcarExpandido`,
      `completado`, `agotado`, `respaldarMinimo`, `esRaiz`).
- [ ] `Nodo.compareTo`, `camino()`, `caminoComoTexto()`, `equals`, `hashCode`.
- [ ] `Frontera`: cola de prioridad acotada (`insertar`, `extraerMejor`, `contiene`,
      `tamano`, `vacia`).
- [ ] `Visitados`: `registrarSiMejor`, `tamano`.
- [ ] `Busqueda`: constructores encadenados y estrategias
      `PROFUNDIDAD`, `ANCHURA`, `COSTO_UNIFORME`, `VORAZ`, `A_ESTRELLA`.
- [ ] `Busqueda.buscar()` + métricas (`nodosExpandidos`, `estadosVisitados`,
      `tiempoMs`).

**Criterio de aceptación:** `solve` encuentra solución con A\* (heurística = 0 basta
para el motor) y `-v` reporta estadísticas coherentes.

## Tarea 3 — Búsqueda acotada, heurísticas y PBD

- [ ] `Frontera.eliminarPeorHojaNoRaiz()` (poda para SMA\*).
- [ ] `Busqueda`: estrategia `A_ESTRELLA_ACOTADA` con memoria limitada por
      `maxNodosArbol` y `limiteVisitadosAlcanzado()`.
- [ ] `Heuristicas.heuristicaManhattanToroidal` (no admisible, rápida).
- [ ] `Heuristicas.heuristicaManhattanAdmisible`.
- [ ] `Heuristicas.heuristicaPermutaciones`.
- [ ] `PBD`: carga de fichero/recurso, `valor(long)`, `valor(Estado)`, `minimo()`.
- [ ] `HeuristicasPBD`: `funcion(Tipo)`, `valorPares`, `valorImpares`, `valorMaximo`,
      `valorSuma`.
- [ ] Recursos `/pdb8.dat` y `/pdb8_impares.dat` (generación local, fuera del repo).

**Criterio de aceptación:** `solve -e A_ESTRELLA -h PBD_8` con heurística admisible
mantiene la optimalidad; `PBD_8_SUMA` documenta que no garantiza óptimo.

## Tarea 4 (documentación) — SDD

- [x] `.gitignore` para versionar solo el código y la documentación.
- [x] Carpeta `.doc/` con `README`, `constitution`, `spec`, `plan`, `tasks` y
      `references`.
- [ ] Mantener actualizada la documentación al cerrar cada tarea.
