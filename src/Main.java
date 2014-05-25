import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Main {

    public static void main (String[] args){
        List<Zona> zonas = new ArrayList<Zona>(6);
        zonas.add(new Zona("uno", 3, 4));
        zonas.add(new Zona("uno", 3, 4));
        zonas.add(new Zona("uno", 3, 4));
        zonas.add(new Zona("uno", 3, 4));
        zonas.add(new Zona("uno", 3, 4));
        zonas.add(new Zona("uno", 3, 4));
        Date[] fecha = new Date[2];
        List<Vendedor> vend = new ArrayList<Vendedor>(1);
        vend.add(new Vendedor());
        Sistema sist = new Sistema(vend);
        Espectaculo espec = new Espectaculo("", 500, "", fecha, zonas);
        Comprador[] comprador = {new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist), new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist),new Comprador(espec,sist)};

        for (Comprador compra:comprador){
            compra.start();
        }
    }
}
