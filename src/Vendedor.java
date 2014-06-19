import java.util.ArrayList;
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

    public Entrada[] vender(String zona, int cantEntradas, String espectaculo) {
        Espectaculo espectaculoSeleccionado = null;
        for (Espectaculo espect : espectaculos){
            if (espect.getNombre().equals(espectaculo)){
                espectaculoSeleccionado = espect;
            }
        }
//        System.out.println(espectaculoSeleccionado.getNombre());
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
            Entrada[] entradasVendidas = new Entrada[cantEntradas];
            for (int i = 0; i < asientos.length; i++) {
                List<Entrada> asientosLibres = new ArrayList<Entrada>(asientos[i].length); //Array que inicializa el largo de la fila
                for (int j = 0; j < asientos[i].length; j++) {
                    if (asientos[i][j].isLibre()) {
                        asientosLibres.add(asientos[i][j]);
                    } else {
                        if (asientosLibres.size() >= cantEntradas) {
                            int h = 0;
                            for (Entrada asiento : asientosLibres) {
                                //Se ocupa el asiento
                                asiento.setLibre(false);
                                //Se setea el numero de asiento, la zona y el precio de la entrada
                                asiento.setZona(zonaSelecionada.getNombre());
                                asiento.setPrecio(zonaSelecionada.getPrecioEntrada());
                            }
                        }
                    }
                }
            }
            //espectaculoSeleccionado.getEntradas().replace(zonaSelecionada, asientos); //DA ERROR AGU...
            // Se libera la zona seleccionada llamando al semaforo de esta
            zonaSelecionada.mutex.release();
            return entradasVendidas;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
