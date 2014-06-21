import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by user on 24/05/2014.
 */
public class Main {

    public static void main (String[] args){

        Date[] fecha;
        JSONParser parser = new JSONParser();
        Espectaculo espectaculo;

        /**
         * Se leen los datos del archivo JSON. El archivo contiene lo necesario para la simulación.
         */
        try {
            JSONObject escenario = (JSONObject) ((JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "\\Escenarios\\EscenarioPrueba3.json"))).get("Escenario");

            //Obtengo todos los espectaculos para el escenario
            JSONArray jEspectaculos =(JSONArray) escenario.get("Espectaculos");

            List<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
            for (int i = 0; i < jEspectaculos.size(); i++) {
                JSONObject jEspectaculo = (JSONObject) jEspectaculos.get(i);

                int cantidadEntradas = Integer.parseInt(jEspectaculo.get("cantidadEntradas").toString());
                String localidad = (String) jEspectaculo.get("lugar");
                String nombreEspectaculo = (String) jEspectaculo.get("nombre");
                String[] fechasEvento = ((String)jEspectaculo.get("fecha")).split(",");



                JSONArray jZonas = (JSONArray)jEspectaculo.get("Zonas");
                List<Zona> zonas = new ArrayList<Zona>();
                for (int j = 0; j < jZonas.size(); j++) {
                    JSONObject jZona = (JSONObject) jZonas.get(j);

                    String nombreZona = (String)jZona.get("nombre");
                    int largoFilas = Integer.parseInt(jZona.get("largoFila").toString());
                    int cantidadFilas = Integer.parseInt(jZona.get("cantFilas").toString());

                    Zona nuevaZona = new Zona(nombreZona,cantidadFilas,largoFilas);
                    zonas.add(nuevaZona);
                }

                espectaculo = new Espectaculo(localidad, cantidadEntradas, nombreEspectaculo,fechasEvento, zonas);
                espectaculos.add(espectaculo);
            }

            //Selecciono todos los locales para el Escenario
            JSONArray jLocales =(JSONArray) escenario.get("Locales");

            List<Local> locales = new CopyOnWriteArrayList<Local>();
            for (int i = 0; i<jLocales.size();i++){

                JSONObject jLocal = (JSONObject)jLocales.get(i);
                String nombreLocal = (String)jLocal.get("nombre");

                //Creo la lista de vendedores para este local
                List<Vendedor> vendedores= new ArrayList<Vendedor>();

                //Selecciono todos los vendedores dentro del local
                JSONArray jVendedores = (JSONArray) jLocal.get("Vendedores");
                for (int j=0; j< jVendedores.size();j++){
                    JSONObject jVendedor = (JSONObject) jVendedores.get(j);

                    int id = Integer.parseInt(jVendedor.get("id").toString());

                    Vendedor vendedorNuevo = new Vendedor(id, espectaculos);
                    vendedores.add(vendedorNuevo);
                }

                Local local = new Local(vendedores,nombreLocal);
                locales.add(local);
            }

            JSONArray jCompradores = (JSONArray) escenario.get("Compradores");

            List<Comprador> compradores = new CopyOnWriteArrayList<Comprador>();
            for (int i = 0; i < jCompradores.size(); i++) {
                JSONObject jComprador = (JSONObject) jCompradores.get(i);

                int idComprador = Integer.parseInt(jComprador.get("idComprador").toString());
                String tipoComprador = (String) jComprador.get("tipoComprador");
                String zona = (String) jComprador.get("zona");
                int cantDeEntradas = Integer.parseInt( jComprador.get("cantDeEntradas").toString());
                String espectaculoSeleccionado = (String) jComprador.get("espectaculo");
                String nombreLocal = (String) jComprador.get("lugarDeCompra");
                Double tiempoLlegada = Double.parseDouble(jComprador.get("tiempoIngreso").toString());
//                //Dado el nombre del local, encuentro en la lista de locales el que le corresponde al comprador
//                Local local = new Local();
//                for(Local l: locales)
//                {
//                    if (l.nombre.equals(nombreLocal))
//                    {
//                        local=l;
//                    }
//                }
                compradores.add( new Comprador(idComprador,tipoComprador,zona,cantDeEntradas, espectaculoSeleccionado, nombreLocal,tiempoLlegada));
            }

            Reloj reloj = new Reloj(compradores,locales);
            reloj.corre();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
