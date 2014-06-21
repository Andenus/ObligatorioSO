import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by user on 19/06/2014.
 */
public class Reloj{

    Double tiempoTrascurrido = 0.0;
    List<Comprador> compradores;
    List<Local> locales;
    Map<Vendedor, Double> vendedoresALiberar = new ConcurrentHashMap<Vendedor, Double>();
    HashMap<Double, ArrayList<ArrayList<String>>> salida = new HashMap<Double, ArrayList<ArrayList<String>>>();
    boolean pasaronTodos = false;
    ManejadorArchivos archivoSalida = new ManejadorArchivos();
    int cont = 0;

    public Reloj(List<Comprador> compradores, List<Local> locales) {
        this.compradores = compradores;
        this.locales = locales;
    }

    public CopyOnWriteArrayList<Comprador> planificar(List<Comprador> compradores) {
        ArrayList<Comprador> prioridad1 = new ArrayList<Comprador>();
        ArrayList<Comprador> prioridad2 = new ArrayList<Comprador>();
        ArrayList<Comprador> sinPrioridad = new ArrayList<Comprador>();
        for (Comprador comp : compradores) {
            String tipo = comp.getTipoComprador();

            if (!tipo.equals("")) {
                if (tipo.equals("P")) {
                    prioridad1.add(comp);
                }
                if (tipo.equals("GC")) {
                    prioridad2.add(comp);
                }
                if (tiempoTrascurrido - comp.getTiempoIngreso() >= 20.0) {
                    comp.setTipoComprador("E");
                    prioridad1.add(comp);
                }
            } else {
                sinPrioridad.add(comp);
            }
        }
        CopyOnWriteArrayList<Comprador> compradoresReordenados = new CopyOnWriteArrayList<Comprador>();
        compradoresReordenados.addAll(prioridad1);
        compradoresReordenados.addAll(prioridad2);
        compradoresReordenados.addAll(sinPrioridad);
        return compradoresReordenados;
    }

    public void corre() {

        try {
            while (!pasaronTodos) {
                ArrayList<ArrayList<String>> nuevoCiclo = new ArrayList<ArrayList<String>>();
                ArrayList<String> ciclo = new ArrayList<String>();
                ciclo.add("");
                ciclo.add("Ciclo "+tiempoTrascurrido.intValue());
                ciclo.add("");
                nuevoCiclo.add(ciclo);
                archivoSalida.escribirArchivo(nuevoCiclo);
                ArrayList <String> temp = new ArrayList<String>();
                for (Comprador comprador : compradores) {
                    if (comprador.getTiempoIngreso().compareTo(tiempoTrascurrido) == 0) {
                        System.out.println("Soy el comprador "+comprador.getIdComprador()+", mi tiempo de entrada es "+comprador.getTiempoIngreso()+" y el tempo transcurrido es "+tiempoTrascurrido);
                        for (Local local : locales) {
                            if (local.nombre.equals(comprador.getLocal())) {
                                local.getCompradores().add(comprador);
                            }
                        }
                        compradores.remove(comprador);
                    }
                }
                for (Local local : locales) {
                    CopyOnWriteArrayList<Comprador> listaAuxiliar = planificar(local.getCompradores());
                    local.setCompradores(listaAuxiliar);
                    if (!local.vendedores.isEmpty()) {
                        for (Vendedor vendedorLibre : local.vendedores) {
                            if (!local.getCompradores().isEmpty()) {
                                for (Comprador comprador : local.getCompradores()) {
                                    comprador.setVendedor(vendedorLibre);
                                    comprador.start();
                                    local.getCompradores().remove(comprador);
                                    local.getCompradoresSiendoAtendidos().add(comprador);
                                    temp.add("Comprador" + comprador.getIdComprador() + " ingreso con prioridad " + comprador.getTipoComprador() + " en el momento " + tiempoTrascurrido);
                                    temp.add("Comprador" + comprador.getIdComprador() + " está esperando ser atendido en " + local.nombre);
                                    temp.add("Comprador" + comprador.getIdComprador() + " se le vendió " + comprador.getCantDeEntradas() + " entradas para el espectaculo " + comprador.getEspectaculo() + " en la zona " + comprador.getZona());
                                    cont++;
                                    temp.add(Integer.toString(cont));
                                    ArrayList<ArrayList<String>> arraySalida = new ArrayList<ArrayList<String>>();
                                    if (salida.containsKey(comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso())) {
                                        for (ArrayList<String> array : salida.get(comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso())) {
                                            arraySalida.add(array);
                                        }
                                    }
                                    arraySalida.add(temp);
                                    salida.put(Math.ceil(comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso()), arraySalida);
                                    vendedoresALiberar.put(comprador.getVendedor(), comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso());
                                }
                            }
                        }
                    }
                    for (Vendedor vendedor : local.vendedoresOcupados) {
                        if (vendedoresALiberar.get(vendedor) != null) {
                            if (vendedoresALiberar.get(vendedor) <= tiempoTrascurrido) {
                                try {
                                    local.liberarVendedor(vendedor);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                if (salida.containsKey(tiempoTrascurrido)) {
                    archivoSalida.escribirArchivo(salida.get(tiempoTrascurrido));
                }
                int compradoresRestantes = 0;
                for (Local local:locales) {
//                    for (Comprador comprador:local.getCompradores()){
//                        compradoresRestantes++;
//                    }
                    for (Comprador comprador:local.getCompradoresSiendoAtendidos()){
                        compradoresRestantes++;
                        if ((comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso())<=tiempoTrascurrido){
                            local.getCompradoresSiendoAtendidos().remove(comprador);
                        }
                    }
                }
                if (compradoresRestantes==0 && compradores.size()==0){
                    pasaronTodos = true;
                }
                tiempoTrascurrido += 1.0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
