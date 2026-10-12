# Especificación funcional

## 1. Descripción

`loopover` resuelve el puzzle **Loopover 4×4** y permite inspeccionar/verificar estados
y secuencias de movimientos desde la línea de comandos. El objetivo de búsqueda es
llegar al **estado resuelto**:

```
00010203040506070809101112131415
```

donde la casilla `i` (fila `i/4`, columna `i%4`) contiene la ficha `i`.

## 2. Modelo del dominio

### 2.1 Tablero

- Rejilla de **4×4 = 16 casillas**, numeradas de 0 a 15 en orden fila-mayor.
- Cada casilla contiene una ficha de 0 a 15 (sin repeticiones).
- Codificación interna: bitboard en un `long` sin signo, **4 bits por casilla**
  (`BITS_POR_CASILLA = 4`). La casilla `i` ocupa los bits `[i*4, i*4+3]`.
- Estado resuelto: `0xFEDCBA98_76543210L`.

### 2.2 Movimientos (acciones)

Un movimiento es **encadenado**: desplaza una fila y una columna a la vez (el elemento
de la intersección participa en ambos). Hay **32 acciones** (`NUM_ACCIONES = 32`):

- 16 con signo `+` y 16 con signo `-`.
- El desplazamiento es **circular** (toroidal): al salir por un borde se reentra por el
  opuesto.
- `+`: la fila se desplaza a la **derecha** y la columna hacia **abajo**.
- `-`: la fila se desplaza a la **izquierda** y la columna hacia **arriba**.

Codificación en 5 bits:

| Bits | Significado |
|---|---|
| 4 | Signo (`1` = `+`, `0` = `-`) |
| 3–2 | Columna (0..3) |
| 1–0 | Fila (0..3) |

Representación textual: `fila` + `columna` + `signo`, por ejemplo `01+`, `33-`.

## 3. Interfaz de línea de comandos

Comando raíz: `loopover` (clase `LoopoverApp`). Incluye `--help` y `--version`.

### 3.1 Subcomando `verify`

```
loopover verify -s <estado> [-a <acciones>]
```

- `-s` (obligatorio): estado como cadena de **32 dígitos** (dos dígitos por casilla).
- `-a` (opcional): lista de acciones separadas por comas, p. ej. `21+,03-`.

Comportamiento:

- **Sin `-a`**: imprime los **32 sucesores** del estado, uno por línea, con formato
  `(acción, estado, costo)`.
- **Con `-a`**: aplica las acciones en orden e imprime el **estado resultante**.
- Estado inválido → mensaje de error por `stderr` y código de salida `1`.
- Acción inválida → mensaje de error por `stderr` y código de salida `1`.

### 3.2 Subcomando `solve`

```
loopover solve -s <estado> [-e <estrategia>] [-p <profundidad>] [-c <capacidad>] [-h <heuristica>] [-m <maxVisitados>] [-v]
```

| Opción | Descripción | Por defecto |
|---|---|---|
| `-s` | Estado inicial (32 dígitos). Obligatorio. | — |
| `-e, --estrategia` | `PROFUNDIDAD`, `ANCHURA`, `COSTO_UNIFORME`, `VORAZ`, `A_ESTRELLA`, `A_ESTRELLA_ACOTADA` | `A_ESTRELLA` |
| `-p, --profundidad` | Profundidad máxima | `1000` |
| `-c, --capacidad` | Máx. nodos en memoria (SMA\*). Obligatorio para `A_ESTRELLA_ACOTADA` | — |
| `-h, --heuristica` | `MANHATTAN`, `CERO`, `PARES_8`, `IMPARES_8`, `PBD_8`, `PBD_8_SUMA`, `MANHATTAN_ADMISIBLE`, `PERMUTACIONES` | `MANHATTAN` |
| `-m, --max-visitados` | Límite de estados visitados (`0` = ilimitado) | `0` |
| `-v, --verbose` | Muestra estadísticas de la búsqueda | `false` |

Comportamiento:

- Si el estado ya está resuelto, imprime `(ya resuelto)` y termina con éxito.
- En caso contrario imprime la **secuencia de acciones** que lleva al estado resuelto.
- Con `-v` añade: `profundidad`, `nodosExpandidos`, `estadosVisitados` y `tiempo` (ms).
- Sin solución dentro de los límites → mensaje por `stderr` y código de salida `1`.
- `A_ESTRELLA_ACOTADA` sin `-c` → error de configuración y código `1`.

## 4. Criterios de aceptación

1. `verify -s <resuelto>` lista 32 sucesores coherentes.
2. `verify -s <estado> -a <acciones>` devuelve el estado obtenido tras aplicar la
   secuencia, y coincide con el que produce la búsqueda.
3. Para cualquier estado resoluble, `solve -e A_ESTRELLA` con una heurística admisible
   encuentra una solución y su secuencia reconstruye el estado resuelto.
4. `solve -v` reporta estadísticas coherentes (nodos expandidos > 0, tiempo ≥ 0).
5. Las estrategias no informadas funcionan sin heurística (heurística = 0).
