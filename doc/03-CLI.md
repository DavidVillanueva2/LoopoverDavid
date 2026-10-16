# 03. CLI (verify)

El subcomando `verify` comprueba estados y aplica acciones. Usa `picocli`.

## Compilar

Desde `loopover/`:
```bash
mvn clean package -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 -DskipTests
```

## Uso

```bash
java -jar target/loopover.jar verify -s <32_digitos> [-a <acciones>]
```

- Sin `-a`: imprime los **32 sucesores**, uno por línea: `(accion,estado,costo)`
- Con `-a`: imprime el **estado resultante** tras aplicar la secuencia (separada por comas)

## Códigos de salida

- `0` si válido y se puede mostrar el resultado
- `1` si hay error (estado inválido o acción inválida); el mensaje va a `stderr`

## Ejemplos

```bash
# 32 sucesores del estado resuelto
java -jar target/loopover.jar verify -s 00010203040506070809101112131415

# Aplicar acción única (ejemplo de la guía)
java -jar target/loopover.jar verify -s 00010203040506070809101112131415 -a "02+"
# -> 03001402040501070809061112131015

# Aplicar secuencia
java -jar target/loopover.jar verify -s 15010203040506070809101112131400 -a "21+,03-"
# -> 13020307040106101105090012081415

# Estado inválido
java -jar target/loopover.jar verify -s 123; echo "exit=$?"
# Error en el estado: El estado debe tener 32 digitos y tiene 3
# exit=1
```

## Comando raíz

```bash
java -jar target/loopover.jar
# Hola, mundo desde Loopover!  (exit 0)
```
