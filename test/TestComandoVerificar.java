import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import picocli.CommandLine;

/**
 * Tests de integracion del subcomando {@code verify} (ComandoVerificar):
 * codigos de salida, stdout con la lista de sucesores / estado resultante y
 * mensajes de error por stderr.
 */
public class TestComandoVerificar extends TestBase {

    private static final String RESUELTO = "00010203040506070809101112131415";

    @Override
    public String nombre() {
        return "ComandoVerificar";
    }

    @Override
    public void ejecutar() {
        listadoDeSucesores();
        aplicarUnaAccion();
        aplicarSecuencia();
        errorDeEstado();
        errorDeAccion();
    }

    private void listadoDeSucesores() {
        Resultado r = ejecutar("-s", RESUELTO);
        igual(0, r.exit, "exit code sin -a");
        String[] lineas = r.out.strip().split("\n");
        igual(32, lineas.length, "numero de lineas de sucesores");
        comprobar(lineas[0].startsWith("(00+,"), "primera linea es el sucesor 00+");
        comprobar(lineas[2].startsWith("(02+,03001402040501070809061112131015,"),
                "tercera linea es el sucesor 02+ con su estado");
        comprobar(lineas[0].endsWith(",1.0)"), "formato con costo 1.0");
        igual("", r.err, "sin -a no hay stderr");
    }

    private void aplicarUnaAccion() {
        Resultado r = ejecutar("-s", RESUELTO, "-a", "02+");
        igual(0, r.exit, "exit code con -a valida");
        igual("03001402040501070809061112131015", r.out.strip(), "estado resultante de 02+");
        igual("", r.err, "sin errores en stderr");
    }

    private void aplicarSecuencia() {
        Resultado r = ejecutar("-s", "15010203040506070809101112131400",
                "-a", "21+,03-");
        igual(0, r.exit, "exit code con secuencia");
        igual("13020307040106101105090012081415", r.out.strip(),
                "estado resultante de la secuencia 21+,03-");
    }

    private void errorDeEstado() {
        Resultado r = ejecutar("-s", "123");
        igual(1, r.exit, "exit code con estado invalido");
        comprobar(r.err.contains("Error en el estado"), "mensaje de error de estado en stderr");
        igual("", r.out, "sin stdout cuando el estado es invalido");
    }

    private void errorDeAccion() {
        Resultado r = ejecutar("-s", RESUELTO, "-a", "44+");
        igual(1, r.exit, "exit code con accion invalida");
        comprobar(r.err.contains("Error en la accion '44+'"),
                "mensaje de error de accion en stderr");
    }

    // ------------------------------------------------------------------------

    private static Resultado ejecutar(String... args) {
        PrintStream outOriginal = System.out;
        PrintStream errOriginal = System.err;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(err, true, StandardCharsets.UTF_8));
            int exit = new CommandLine(new ComandoVerificar()).execute(args);
            return new Resultado(exit, out.toString(StandardCharsets.UTF_8),
                    err.toString(StandardCharsets.UTF_8));
        } finally {
            System.setOut(outOriginal);
            System.setErr(errOriginal);
        }
    }

    private static final class Resultado {
        final int exit;
        final String out;
        final String err;

        Resultado(int exit, String out, String err) {
            this.exit = exit;
            this.out = out;
            this.err = err;
        }
    }
}
