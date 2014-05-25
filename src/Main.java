import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 24/05/2014.
 */
public class Main {

    public static void main (String[] args){
        /*List<Zona> zonas = new ArrayList<Zona>(6);
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
        }*/
        List<Zona> zonas;
        Date[] fecha;
        JSONParser parser = new JSONParser();
        List<Vendedor> vendedores;
        Sistema sistema;
        Espectaculo especctaculo;
        Comprador[] compradores;
        try {
            JSONObject escenario = (JSONObject) ((JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "\\Escenarios\\EscenariosPrueba.json"))).get("Escenario");
            zonas = new ArrayList<Zona>(((JSONObject)escenario.get("zonas")).size());

        } catch (Exception e){

        }
    }
}
