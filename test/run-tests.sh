#!/usr/bin/env bash
#
# Compila src/ y test/ y ejecuta toda la bateria de tests.
# No usa Maven (el pom.xml no define tests ni un plugin de compilacion moderno).
#
# Uso:  test/run-tests.sh
#
set -euo pipefail

RAIZ="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
M2="${HOME}/.m2/repository"

# Dependencias (mismas versiones que el pom.xml)
CP="${M2}/info/picocli/picocli/4.7.7/picocli-4.7.7.jar"
CP="${CP}:${M2}/it/unimi/dsi/fastutil/8.5.16/fastutil-8.5.16.jar"
CP="${CP}:${M2}/com/google/guava/guava/33.7.1-jre/guava-33.7.1-jre.jar"

SALIDA="${RAIZ}/test/build"
rm -rf "${SALIDA}"
mkdir -p "${SALIDA}"

echo "Compilando src/ y test/ ..."
javac --release 17 -cp "${CP}" -d "${SALIDA}" "${RAIZ}"/src/*.java "${RAIZ}"/test/*.java

echo "Ejecutando tests ..."
java -cp "${SALIDA}:${CP}" TestTodos
