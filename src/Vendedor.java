import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Vendedor {
    int idVendedor;

    public void vender(Zona zona, int cantEntradas, Espectaculo espectaculo) {
        List<Entrada> entradas = espectaculo.getEntradas();
        Asiento[][] asientos = espectaculo.getAsientos().get(zona);
        //Metodo complejo que asigna asientos...
        for (int i=0; i < asientos.length; i++){
            int asientosLibres = 0;
            for (int j=0; j < asientos[i].length; j++){
                if (asientos[i][j].isLibre()){
                    asientosLibres++;
                } else {
                    if (asientosLibres >= cantEntradas){
                        daasientos;
                    }
                }
            }
        }
        System.out.println("El vendedor me dio un asiento");
    }

}
