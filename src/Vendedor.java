import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Vendedor {
    int idVendedor;

    public Vendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Entrada[] vender(String zona, int cantEntradas, Espectaculo espectaculo) {
        List<Zona> zonasALaVenta = espectaculo.getZonas();
        Zona zonaSelecionada = null;
        for (Zona zonaARecorrer:zonasALaVenta){
            if (zonaARecorrer.getNombre().equals(zona)){
                zonaSelecionada = zonaARecorrer;
            }
        };
        List<Entrada> entradas = espectaculo.getEntradas();
        Asiento[][] asientos = espectaculo.getAsientos().get(zona);
        Entrada[] entradasVendidas = new Entrada[cantEntradas];
        for (int i=0; i < asientos.length; i++){
            List<Asiento> asientosLibres = new ArrayList<Asiento>(asientos[i].length);
            for (int j=0; j < asientos[i].length; j++){
                if (asientos[i][j].isLibre()){
                    asientosLibres.add(asientos[i][j]);
                } else {
                    if (asientosLibres.size() >= cantEntradas){
                        int h = 0;
                        for (Asiento asiento:asientosLibres){
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
        espectaculo.setEntradas(entradas);
        espectaculo.getAsientos().replace(zonaSelecionada,asientos);
        return entradasVendidas;
    }

}
