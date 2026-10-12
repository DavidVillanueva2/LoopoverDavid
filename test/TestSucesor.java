/**
 * Tests de Sucesor: tupla inmutable (accion, estado, costo) y su toString.
 */
public class TestSucesor extends TestBase {

    @Override
    public String nombre() {
        return "Sucesor";
    }

    @Override
    public void ejecutar() {
        Estado estado = new Estado("00010203040506070809101112131415");
        int accion = Estado.accionDesde("02+");
        Sucesor s = new Sucesor(accion, estado, 1.0f);

        igual(accion, s.accion(), "accion() devuelve la accion inyectada");
        comprobar(s.estado() == estado, "estado() devuelve el mismo objeto estado");
        igual(1.0f, s.costo(), "costo() devuelve el costo inyectado");
        igual("(02+," + estado + ",1.0)", s.toString(), "formato de toString()");
    }
}
