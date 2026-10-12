import java.util.Objects;

/**
 * Base minima para los tests del proyecto (sin dependencias externas).
 * Cada test extiende esta clase e implementa {@link #ejecutar()}.
 */
public abstract class TestBase {

    private int ok;
    private int fallos;

    /** Nombre legible del test (usado por el runner). */
    public abstract String nombre();

    /** Cuerpo del test; usa los metodos comprobar/igual/lanzaError. */
    public abstract void ejecutar();

    protected void comprobar(boolean condicion, String mensaje) {
        if (condicion) {
            ok++;
        } else {
            fallos++;
            System.out.println("  FALLO: " + mensaje);
        }
    }

    protected void igual(Object esperado, Object real, String mensaje) {
        comprobar(Objects.equals(esperado, real),
                mensaje + " (esperado=" + esperado + ", real=" + real + ")");
    }

    /** Comprueba que la operacion lanza IllegalArgumentException. */
    protected void lanzaError(Runnable operacion, String mensaje) {
        try {
            operacion.run();
            comprobar(false, mensaje + " (no lanzo excepcion)");
        } catch (IllegalArgumentException e) {
            comprobar(true, mensaje);
        }
    }

    public int ok() {
        return ok;
    }

    public int fallos() {
        return fallos;
    }
}
