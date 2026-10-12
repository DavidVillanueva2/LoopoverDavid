# Plan técnico

## 1. Estructura de paquetes y clases

El proyecto usa el **paquete por defecto** (`src/` es el `sourceDirectory`). Clases:

| Clase | Responsabilidad | Estado |
|---|---|---|
| `LoopoverApp` | Comando raíz `picocli`, registra los subcomandos. | Base |
| `ComandoVerificar` | Subcomando `verify`. | Base |
| `ComandoBusqueda` | Subcomando `solve`. | Base |
| `Estado` | Tablero como bitboard + acciones. | **Tarea 1 (TODO)** |
| `Sucesor` | Tupla (acción, estado, costo). | Completa |
| `Nodo` | Nodo del árbol de búsqueda. | **Tarea 2 (TODO)** |
| `Frontera` | Cola de prioridad min-max. | **Tarea 2/3 (TODO)** |
| `Visitados` | Tabla de estados visitados. | **Tarea 2 (TODO)** |
| `Busqueda` | Motor de búsqueda (6 estrategias). | **Tarea 2 (TODO)** |
| `Heuristicas` | Manhattan toroidal/admisible, permutaciones. | **Tarea 3 (TODO)** |
| `PBD` | Pattern Database de 8 piezas. | **Tarea 3 (TODO)** |
| `HeuristicasPBD` | Fachada de heurísticas PBD (pares/impares). | **Tarea 3 (TODO)** |

## 2. Representación del estado (`Estado`)

- Bitboard en `long`: 4 bits por casilla, casilla `i` en bits `[i*4, i*4+3]`.
- Construcción desde `long`, `String` (32 dígitos) o `int[16]`, con validación de
  dominio y ausencia de duplicados.
- Operaciones internas:
  - `desplazarFila(bitboard, fila, positivo)`: rotación circular de los 16 bits de la
    fila (máscara `0xFFFF` desplazada a `fila*16`).
  - `desplazarColumna(bitboard, columna, positivo)`: rotación de los 4 nibbles
    separados 16 bits (máscara `0x000F000F000F000F`).
- `aplicar(accion)` = desplazarFila + desplazarColumna según el signo.
- `sucesores()` = 32 `Sucesor` (uno por acción), costo `1.0f`.

## 3. Búsqueda

### 3.1 Nodo (`Nodo`)

Árbol de búsqueda con, al menos: estado, padre, acción, profundidad, costo acumulado,
heurística `h`, valor `f`, y banderas contables de hijos expandidos/pendientes
(`marcarExpandido`, `restarHijo`, `respaldarMinimo`, `reexpandir`, `completado`,
`agotado`) necesarias para **SMA\***. `compareTo` ordena por `f` (y desempate estable).
`camino()`/`caminoComoTexto()` reconstruyen la secuencia de acciones.
`equals`/`hashCode` sobre el estado.

### 3.2 Frontera (`Frontera`)

Cola de prioridad acotada por `maxNodosArbol`:
- `insertar`, `extraerMejor`, `contiene`, `tamano`, `vacia`.
- `eliminarPeorHojaNoRaiz()` (Tarea 3): poda para SMA\*.

### 3.3 Visitados (`Visitados`)

Tabla hash de bitboards → mejor valor conocido.
- `registrarSiMejor(long estado, float valor)`: inserta/actualiza solo si mejora.
- `tamano()`.

### 3.4 Motor (`Busqueda`)

Estrategias (`enum Estrategia`):

| Estrategia | Estructura | Criterio de extracción | ¿Óptima? |
|---|---|---|---|
| `PROFUNDIDAD` | Pila (LIFO) | Profundidad | No |
| `ANCHURA` | Cola (FIFO) | Profundidad | Sí (costo 1) |
| `COSTO_UNIFORME` | Cola de prioridad | `g` | Sí |
| `VORAZ` | Cola de prioridad | `h` | No |
| `A_ESTRELLA` | Cola de prioridad | `f = g + h` | Sí (si `h` admisible) |
| `A_ESTRELLA_ACOTADA` | Cola de prioridad + poda | `f`, con memoria acotada por `maxNodosArbol` | SMA\* |

Constructores encadenados: `(estado, estrategia, profundidad)`,
`(+ maxNodosArbol)`, `(+ heuristica)`, `(+ maxVisitados)`.
`buscar()` devuelve el `Nodo` solución o `null`; expone `nodosExpandidos()`,
`estadosVisitados()`, `tiempoMs()` y `limiteVisitadosAlcanzado()`.

## 4. Heurísticas (Tarea 3)

- `heuristicaManhattanToroidal`: distancia Manhattan contando la topología toroidal
  (**no** admisible en general, usada como cota rápida).
- `heuristicaManhattanAdmisible`: versión admisible.
- `heuristicaPermutaciones`: basada en la paridad/permutación de fichas.
- **PBD**: `PBD` carga una Pattern Database de 8 piezas (`TAMANNO = 32_432_400`) desde
  fichero o recurso. `HeuristicasPBD` combina dos PBD disjuntas (pares e impares):
  - `PARES_8`, `IMPARES_8`: valor individual.
  - `PBD_8`: **máximo** de ambas (admisible).
  - `PBD_8_SUMA`: **suma** de ambas (no admisible → no garantiza óptimo).
- Recursos esperados: `/pdb8.dat` y `/pdb8_impares.dat` (**generados localmente**, no
  versionados).

## 5. Build y ejecución

```bash
mvn -q package            # genera target/loopover.jar (shade, mainClass=LoopoverApp)
java -jar target/loopover.jar verify -s 00010203040506070809101112131415
java -jar target/loopover.jar solve -s <estado> -e A_ESTRELLA -v
```
