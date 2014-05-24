import java.util.Date;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Espectaculo {
    List<Entrada> entradas;
    int cantidadEntradas;
    int[] asientos;
    String nombre;
    Date[] fecha;
    String localidad;

    public Espectaculo(String localidad, int cantidadEntradas, int[] asientos, String nombre,
                       Date[] fecha) {

        this.localidad = localidad;
        this.cantidadEntradas = cantidadEntradas;
        this.asientos = asientos;
        this.nombre = nombre;
        this.fecha = fecha;

        for (int i = 1; i == cantidadEntradas; i++) {
            Entrada entrada = new Entrada(i);
            entradas.add(entrada);
        }
    }
}
