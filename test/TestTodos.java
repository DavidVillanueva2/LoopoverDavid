/**
 * Runner de todos los tests del proyecto.
 *
 * Uso: {@code test/run-tests.sh} (compila src y test y ejecuta esta clase).
 * Salida: resumen por clase y total; exit code 0 si todo pasa, 1 si hay fallos.
 */
public class TestTodos {

    public static void main(String[] args) {
        TestBase[] tests = {
            new TestEstado(),
            new TestSucesor(),
            new TestComandoVerificar()
        };

        int ok = 0;
        int fallos = 0;
        for (TestBase test : tests) {
            test.ejecutar();
            ok += test.ok();
            fallos += test.fallos();
            String marca = test.fallos() == 0 ? "OK" : "FALLO";
            System.out.println("[" + marca + "] " + test.nombre()
                    + ": " + test.ok() + " ok, " + test.fallos() + " fallos");
        }

        System.out.println();
        System.out.println("TOTAL: " + ok + " ok, " + fallos + " fallos");
        System.exit(fallos == 0 ? 0 : 1);
    }
}
