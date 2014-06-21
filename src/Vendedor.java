import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Vendedor {
    int idVendedor;
    List<Espectaculo> espectaculos;

    public Vendedor(int idVendedor, List<Espectaculo> espectaculos) {
        this.idVendedor = idVendedor;
        this.espectaculos = espectaculos;
    }

    public List<Entrada> vender(String zona, int cantEntradas, String espectaculo) {
        Espectaculo espectaculoSeleccionado = null;
        for (Espectaculo espect : espectaculos) {
            if (espect.getNombre().equals(espectaculo)) {
                espectaculoSeleccionado = espect;
            }
        }
        if (espectaculoSeleccionado==null){
            return null;
        }
        //System.out.println(espectaculoSeleccionado.getNombre());
        try {
            List<Zona> zonasALaVenta = espectaculoSeleccionado.getZonas();
            Zona zonaSelecionada = null;
            for (Zona zonaARecorrer : zonasALaVenta) {
                if (zonaARecorrer.getNombre().equals(zona)) {
                    zonaSelecionada = zonaARecorrer;
                }
            }
            // Se bloquea la zona seleccionada llamando al semaforo de esta
            zonaSelecionada.mutex.acquire();
            Entrada[][] asientos = espectaculoSeleccionado.getEntradas().get(zonaSelecionada);

            List<Entrada> asientosLibres = new ArrayList<Entrada>();
            List<Entrada> asientosVendidos = new ArrayList<Entrada>();

            for (int i = 0; i < asientos.length; i++) {
                for (int j = 0; j < asientos[i].length; j++) {
                    if (asientos[i][j].isLibre()) {
                        asientosLibres.add(asientos[i][j]);
                        if (asientosLibres.size() == cantEntradas) {
                            for (Entrada asiento : asientosLibres) {
                                //Se ocupa el asiento
                                asiento.setLibre(false);
                                //Se setea el numero de asiento, la zona y el precio de la entrada
                                asiento.setZona(zonaSelecionada.getNombre());
                                asiento.setPrecio(zonaSelecionada.getPrecioEntrada());
                                asientosVendidos.add(asiento);
                            }
                        }
                    }
                }
            }
            espectaculoSeleccionado.getEntradas().replace(zonaSelecionada, asientos);
            // Se libera la zona seleccionada llamando al semaforo de esta
            zonaSelecionada.mutex.release();
            return asientosVendidos;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
