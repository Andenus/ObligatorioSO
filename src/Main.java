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
        Espectaculo espectaculo;
        Comprador[] compradores;

        /**
         * Se leen los datos del archivo JSON. El archivo contiene lo necesario para la simulación.
         */
        try {
            JSONObject escenario = (JSONObject) ((JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "\\Escenarios\\EscenarioPrueba.json"))).get("Escenario");
            JSONObject espectaculoDatos = (JSONObject) escenario.get("Espectaculo");
            zonas = new ArrayList<Zona>(((JSONObject)espectaculoDatos.get("zonas")).toString().split("},").length);


            for (int i = 1; i < 3; i++) {
                String nombreNuevaZona = (String)((JSONObject)(((JSONObject) espectaculoDatos.get("zonas")).get("zona"+i))).get("nombre");
                int largoFilas = ((Number) ((JSONObject)(((JSONObject) espectaculoDatos.get("zonas")).get("zona"+i))).get("largoFila")).intValue();
                int cantidadFilas = ((Number) ((JSONObject)(((JSONObject) espectaculoDatos.get("zonas")).get("zona"+i))).get("cantFilas")).intValue();
                Zona nuevaZona = new Zona(nombreNuevaZona,cantidadFilas,largoFilas);
                zonas.add(nuevaZona);
            }

            String[] fechasEvento = ((String)espectaculoDatos.get("fecha")).split(",");
            fecha = new Date[fechasEvento.length];
            for (int i = 0; i < fecha.length; i++) {
                fecha[i] = new Date(fechasEvento[i]);
            }

            int cantidadEntradas = ((Number) espectaculoDatos.get("cantidadEntradas")).intValue();
            String localidad = (String) espectaculoDatos.get("localidad");
            String nombreEspectaculo = (String) espectaculoDatos.get("nombre");
            espectaculo = new Espectaculo(localidad, cantidadEntradas, nombreEspectaculo,fecha, zonas);

            vendedores = new ArrayList<Vendedor>((escenario.get("Sistema")).toString().split(",").length);
            for (int i = 1; i < 4; i++) {
                Vendedor vendedorNuevo = new Vendedor(((Number)((JSONObject) escenario.get("Sistema")).get("vendedor"+i)).intValue());
                vendedores.add(vendedorNuevo);
            }

            sistema = new Sistema(vendedores);

            compradores = new Comprador[((JSONObject) escenario.get("Compradores")).size()];
            for (int i = 0; i < compradores.length; i++) {
                JSONObject comprador = (JSONObject) ((JSONObject) escenario.get("Compradores")).get("comprador"+(i+1));
                int idComprador = ((Number)  comprador.get("idComprador")).intValue();
                String tipoComprador = (String) comprador.get("tipoComprador");
                String zona = (String) comprador.get("zona");
                int cantDeEntradas = ((Number) comprador.get("cantDeEntradas")).intValue();
                compradores[i]= new Comprador(idComprador,tipoComprador,zona,cantDeEntradas, espectaculo, sistema);
            }

            for (Comprador comprador:compradores){
                comprador.start();
            }



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
