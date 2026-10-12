import java.util.Arrays;
import java.util.Random;

/**
 * Tests de Estado (Tarea 1): bitboard, constructores, consultas, aplicar,
 * sucesores, utilidades de acciones y validaciones.
 *
 * La verificacion principal contrasta cada {@code aplicar(accion)} contra un
 * modelo de referencia en cuadricula (desplaza la fila y luego la columna,
 * celda a celda), evitando depender del propio bitboard.
 */
public class TestEstado extends TestBase {

    private static final String RESUELTO = "00010203040506070809101112131415";

    @Override
    public String nombre() {
        return "Estado";
    }

    @Override
    public void ejecutar() {
        estadoResuelto();
        ejemploDeLaGuia();
        sucesores();
        modeloDeReferencia();
        formatosDeAccion();
        validaciones();
        igualdadYHash();
    }

    // ------------------------------------------------------------------------

    private void estadoResuelto() {
        Estado e = new Estado(RESUELTO);
        comprobar(e.esResuelto(), "esResuelto() true en el estado resuelto");
        igual(RESUELTO, e.toString(), "toString() del estado resuelto");
        for (int i = 0; i < 16; i++) {
            igual(i, e.ficha(i), "ficha(" + i + ") del estado resuelto");
        }
        igual(5, e.ficha(1, 1), "ficha(1,1) del estado resuelto");
    }

    private void ejemploDeLaGuia() {
        // loopover_engranado_4x4.md: 02+ (fila 0, columna 2, derecha)
        Estado e = new Estado(RESUELTO);
        Estado r = e.aplicar(Estado.accionDesde("02+"));
        igual("03001402040501070809061112131015", r.toString(), "aplicar(02+) segun la guia");
        comprobar(!r.esResuelto(), "02+ no deja el estado resuelto");
        comprobar(e.esResuelto(), "aplicar() no muta el estado original");
    }

    private void sucesores() {
        Estado e = new Estado(RESUELTO);
        var lista = e.sucesores();
        igual(32, lista.size(), "numero de sucesores");
        igual("00+", Estado.accionComoTexto(lista.get(0).accion()), "primer sucesor");
        igual("33-", Estado.accionComoTexto(lista.get(31).accion()), "ultimo sucesor");
        igual("03001402040501070809061112131015",
                lista.get(2).estado().toString(), "tercer sucesor (02+)");
        igual(1.0f, lista.get(0).costo(), "costo de los sucesores");
        comprobar(lista.get(0).estado().equals(e.aplicar(Estado.accionDesde("00+"))),
                "sucesor(00+) coincide con aplicar(00+)");
    }

    private void modeloDeReferencia() {
        String[] estados = {
            RESUELTO,
            "15010203040506070809101112131400",
            "01020304050607080910111213141500",
            "00010203040506070809101112131415"
        };
        Random rnd = new Random(77L);
        for (int n = 0; n < 25; n++) {
            estados = Arrays.copyOf(estados, estados.length + 1);
            estados[estados.length - 1] = permutacionAleatoria(rnd);
        }
        int comparaciones = 0;
        for (String s : estados) {
            Estado st = new Estado(s);
            int[] grilla = aGrilla(st);
            for (int accion = 0; accion < 32; accion++) {
                String esperado = grillaTexto(moverReferencia(grilla, accion));
                igual(esperado, st.aplicar(accion).toString(),
                        "accion " + Estado.accionComoTexto(accion) + " sobre " + s);
                comparaciones++;
            }
        }
        comprobar(comparaciones >= 32 * 25, "comparaciones contra el modelo de referencia");
    }

    private void formatosDeAccion() {
        for (int accion = 0; accion < 32; accion++) {
            String texto = Estado.accionComoTexto(accion);
            igual(accion, Estado.accionDesde(texto),
                    "ida y vuelta accionComoTexto/accionDesde de " + texto);
        }
        igual("00+", Estado.accionComoTexto(16), "accionComoTexto(16) es 00+");
        igual("00-", Estado.accionComoTexto(0), "accionComoTexto(0) es 00-");
        igual("33+", Estado.accionComoTexto(31), "accionComoTexto(31) es 33+");
        igual("33-", Estado.accionComoTexto(15), "accionComoTexto(15) es 33-");
        igual(16, Estado.accionDesde("00+"), "accionDesde(00+) es 16");
        igual(0, Estado.accionDesde("00-"), "accionDesde(00-) es 0");
        igual(31, Estado.accionDesde("33+"), "accionDesde(33+) es 31");
        igual(15, Estado.accionDesde("33-"), "accionDesde(33-) es 15");
    }

    private void validaciones() {
        lanzaError(() -> new Estado("0001020304050607080910111213141"),
                "estado de 31 caracteres");
        lanzaError(() -> new Estado("000102030405060708091011121314150"),
                "estado de 33 caracteres");
        lanzaError(() -> new Estado("0X010203040506070809101112131415"),
                "estado con caracter no digito");
        lanzaError(() -> new Estado("16010203040506070809101112131415"),
                "estado con ficha 16");
        lanzaError(() -> new Estado("00010203040506070809101112131414"),
                "estado con ficha duplicada");
        lanzaError(() -> new Estado((String) null), "estado null");
        lanzaError(() -> new Estado(new int[15]), "array de longitud incorrecta");
        lanzaError(() -> new Estado(new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,14}),
                "array con ficha duplicada");

        lanzaError(() -> Estado.accionDesde("44+"), "accion con fila fuera de rango");
        lanzaError(() -> Estado.accionDesde("04+"), "accion con columna fuera de rango");
        lanzaError(() -> Estado.accionDesde("0a+"), "accion con caracter no digito");
        lanzaError(() -> Estado.accionDesde("01x"), "accion con signo invalido");
        lanzaError(() -> Estado.accionDesde("01"), "accion con longitud 2");
        lanzaError(() -> Estado.accionDesde(""), "accion vacia");
        lanzaError(() -> Estado.accionDesde((String) null), "accion null");
        lanzaError(() -> Estado.accionComoTexto(32), "accionComoTexto fuera de rango");

        Estado e = new Estado(RESUELTO);
        lanzaError(() -> e.ficha(-1), "ficha(casilla) negativa");
        lanzaError(() -> e.ficha(16), "ficha(casilla) fuera de rango");
        lanzaError(() -> e.aplicar(-1), "aplicar con accion negativa");
        lanzaError(() -> e.aplicar(32), "aplicar con accion fuera de rango");

        // Regresion: coordenadas fuera de rango no deben "caer" en otra casilla
        lanzaError(() -> e.ficha(0, 5), "ficha(0,5) con columna fuera de rango");
        lanzaError(() -> e.ficha(1, -1), "ficha(1,-1) con columna negativa");
        lanzaError(() -> e.ficha(5, 0), "ficha(5,0) con fila fuera de rango");
        lanzaError(() -> e.ficha(-1, 0), "ficha(-1,0) con fila negativa");
    }

    private void igualdadYHash() {
        Estado a = new Estado(RESUELTO);
        Estado b = new Estado(RESUELTO);
        comprobar(a.equals(b), "equals entre estados iguales");
        igual(a.hashCode(), b.hashCode(), "hashCode de estados iguales");
        comprobar(!a.equals(a.aplicar(Estado.accionDesde("00+"))),
                "equals entre estados distintos");
        comprobar(!a.equals(null) && !a.equals("texto"), "equals con otros tipos");
    }

    // ------------------------------------------------------------------------
    // Modelo de referencia en cuadricula
    // ------------------------------------------------------------------------

    private static int[] aGrilla(Estado st) {
        int[] g = new int[16];
        for (int i = 0; i < 16; i++) {
            g[i] = st.ficha(i);
        }
        return g;
    }

    private static int[] moverReferencia(int[] g, int accion) {
        int fila = accion & 0b11;
        int columna = (accion >>> 2) & 0b11;
        boolean positivo = (accion & 0b10000) != 0;
        int[] r = g.clone();
        for (int c = 0; c < 4; c++) {
            int origen = positivo ? (c - 1 + 4) % 4 : (c + 1) % 4;
            r[fila * 4 + c] = g[fila * 4 + origen];
        }
        int[] s = r.clone();
        for (int f = 0; f < 4; f++) {
            int origen = positivo ? (f - 1 + 4) % 4 : (f + 1) % 4;
            s[f * 4 + columna] = r[origen * 4 + columna];
        }
        return s;
    }

    private static String grillaTexto(int[] g) {
        StringBuilder sb = new StringBuilder(32);
        for (int v : g) {
            if (v < 10) {
                sb.append('0');
            }
            sb.append(v);
        }
        return sb.toString();
    }

    private static String permutacionAleatoria(Random rnd) {
        int[] g = new int[16];
        for (int i = 0; i < 16; i++) {
            g[i] = i;
        }
        for (int i = 15; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int t = g[i];
            g[i] = g[j];
            g[j] = t;
        }
        return grillaTexto(g);
    }
}
