# 02. CLASE ESTADO

## Bitboard

- 4 bits por casilla (`BITS_POR_CASILLA = 4`)
- Casilla `i` = fila `i/4`, columna `i%4` ocupa `[i*4, i*4+3]`
- Casilla `(0,0)` en los bits más bajos
- Estado resuelto: `0xFEDCBA98_76543210L` (nibble `i` = ficha `i`)

## Acciones (32)

Codificación 5 bits:
| Bits | Significado |
|---|---|
| 4 | signo (`1` = `+`, `0` = `-`) |
| 3–2 | columna (0..3) |
| 1–0 | fila (0..3) |

Texto: `<fila><columna><signo>` (ej. `02+`, `33-`)

## Movimiento engranado

1. Desplazar **fila** indicada circularmente (`+` derecha, `-` izquierda)
2. Desplazar **columna** indicada circularmente (`+` abajo, `-` arriba)

Esto coincide con `loopover_engranado_4x4.md` (guía de modelado local).

## Métodos clave (resumen)

- `Estado(String)` — 32 dígitos, 2 por casilla. Lanza `IllegalArgumentException` si inválido.
- `ficha(int casilla)` / `ficha(int fila,int columna)` — válidas en rango; `ficha(fila,columna)` valida coordenadas.
- `aplicar(int)` — devuelve **nuevo** `Estado` (no muta).
- `sucesores()` — 32 `Sucesor` con `costo = 1.0f`.
- `accionDesde(String)` / `accionComoTexto(int)` — conversión bidireccional.

## Nota: `00+,00-` no anulan

Como el desplazamiento de fila altera la intersección antes de mover la columna,
la secuencia `(r,c,+),(r,c,-)` **no** devuelve al estado inicial. La verificación
correcta es comparar `aplicar(accion)` con el modelo de referencia celda a celda.
