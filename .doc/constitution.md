# Constitución del proyecto

Reglas y principios que rigen el desarrollo de `loopover`. Todo cambio debe respetarlas.

## 1. Principios

1. **Corrección verificable**: cada comportamiento debe poder comprobarse con el
   comando `verify` y/o pruebas explícitas. No se da por bueno nada que no se pueda
   reproducir.
2. **Admisibilidad y optimalidad**: cuando una estrategia declare ser óptima
   (p. ej. A\*), la heurística usada debe ser **admisible**. Si una heurística no es
   admisible, debe documentarse claramente que no garantiza el óptimo.
3. **Rendimiento primero**: el espacio de estados 4×4 es enorme. Se priorizan
   representaciones compactas (bitboards, `long`) y colecciones primitivas
   (`fastutil`) frente a objetos pesados.
4. **Sin dependencias innecesarias**: solo se añaden librerías justificadas
   (actualmente `picocli`, `fastutil`, `guava`).
5. **Reproducibilidad**: la build se hace con Maven y `mvn package` produce un JAR
   ejecutable autocontenido.

## 2. Reglas técnicas

- **Java 17** (`maven.compiler.release=17`).
- El código fuente vive en `src/` (`sourceDirectory` personalizado en `pom.xml`).
- Los recursos generados localmente (`*.dat`, `target/`, artefactos de IDE) **no** se
  versionan; ver `.gitignore`.
- Nombres de clases, métodos y comentarios en **español**, siguiendo el estilo del
  código existente.
- Las APIs públicas ya definidas en el esqueleto **no** deben romperse: otros módulos
  (CLI, heurísticas, búsqueda) dependen de sus firmas.

## 3. Flujo de trabajo

1. Actualizar/crear la especificación (`spec.md`) si cambia el comportamiento.
2. Reflejar el diseño en `plan.md`.
3. Desglosar el trabajo en `tasks.md`.
4. Implementar.
5. Verificar con `verify`/`solve` y, cuando existan, con las pruebas.
