# 04. TESTS LOCALES

## TestEstado.java

En `test/TestEstado.java` hay un test auxiliar que verifica la Tarea 1:

- Estado resuelto (toString, esResuelto, ficha)
- Ejemplo de la guía (`02+` → `03001402040501070809061112131015`)
- 32 sucesores (orden, primer/último, 3º, costo)
- **32 acciones × 29 estados** comparados con **modelo de referencia celda a celda** (fila primero, columna después)
- Formatos `accionComoTexto`/`accionDesde` (ida y vuelta)
- Validaciones de entradas inválidas (longitud, caracteres, duplicados, rangos)
- Regresión: `ficha(fila,columna)` valida coordenadas
- `equals`/`hashCode` e inmutabilidad (`aplicar` no muta)

Total: **1025 comprobaciones OK** (tal como se ejecutó con el runner anterior).

## Ejecutar sin Maven

No hace falta compilar todo con Maven para ejecutar este test. Usa directamente `javac`/`java`:

```bash
CP="$HOME/.m2/repository/info/picocli/picocli/4.7.7/picocli-4.7.7.jar:$HOME/.m2/repository/it/unimi/dsi/fastutil/8.5.16/fastutil-8.5.16.jar:$HOME/.m2/repository/com/google/guava/guava/33.7.1-jre/guava-33.7.1-jre.jar"

mkdir -p /tmp/testbuild
javac --release 17 -cp "$CP" -d /tmp/testbuild src/*.java test/TestEstado.java
java -cp "/tmp/testbuild:$CP" TestEstado
```

Saldrá algo como:
```
[OK] Estado: 1025 ok, 0 fallos
TOTAL: 1025 ok, 0 fallos
```

## Notas

- `TestEstado.java` no está enlazado con el build de Maven (`pom.xml` no define tests).
- Los tests auxiliares (`TestBase`, `TestSucesor`, `TestComandoVerificar`, `TestTodos`, `run-tests.sh`) fueron eliminados; solo queda `TestEstado.java`.
- El test no interfiere con `mvn package` ni con la ejecución de `java -jar`.
