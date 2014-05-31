import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Vendedor {
    int idVendedor;
    ArrayList<Espectaculo> espectaculos;

    public Vendedor(int idVendedor, ArrayList<Espectaculo> espectaculos) {
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
            espectaculoSeleccionado.mutex.acquire();
            List<Zona> zonasALaVenta = espectaculoSeleccionado.getZonas();
            Zona zonaSelecionada = null;
            for (Zona zonaARecorrer : zonasALaVenta) {
                if (zonaARecorrer.getNombre().equals(zona)) {
                    zonaSelecionada = zonaARecorrer;
                }
            }
            List<Entrada> entradas = espectaculoSeleccionado.getEntradas();
            Asiento[][] asientos = espectaculoSeleccionado.getAsientos().get(zonaSelecionada);
            Entrada[] entradasVendidas = new Entrada[cantEntradas];
            for (int i = 0; i < asientos.length; i++) {
                List<Asiento> asientosLibres = new ArrayList<Asiento>(asientos[i].length); //Array que inicializa el largo de la fila
                for (int j = 0; j < asientos[i].length; j++) {
                    if (asientos[i][j].isLibre()) {
                        asientosLibres.add(asientos[i][j]);
                    } else {
                        if (asientosLibres.size() >= cantEntradas) {
                            int h = 0;
                            for (Asiento asiento : asientosLibres) {
                                //Se ocupa el asiento
                                asiento.setLibre(false);
                                //Se setea el numero de asiento, la zona y el precio de la entrada
                                Entrada entradaAAsignar = entradas.get(0);
                                entradaAAsignar.setNumAsiento(asiento.getNumero());
                                entradaAAsignar.setZona(zonaSelecionada.getNombre());
                                entradaAAsignar.setPrecio(zonaSelecionada.getPrecioEntrada());
                                //Se vende la entrada
                                entradasVendidas[h] = entradas.get(0);
                                h++;
                                //Se saca la entrada de la lista de entradas no vendidas
                                entradas.remove(0);
                            }
                        }
                    }
                }
            }
            espectaculoSeleccionado.setEntradas(entradas);
            espectaculoSeleccionado.getAsientos().replace(zonaSelecionada, asientos);
            espectaculoSeleccionado.mutex.release();
            return entradasVendidas;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
