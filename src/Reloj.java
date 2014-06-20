import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by user on 19/06/2014.
 */
public class Reloj{

    Double tiempoTrascurrido = 0.0;
    List<Comprador> compradores;
    List<Local> locales;
    Map<Vendedor, Double> vendedoresALiberar = new ConcurrentHashMap<Vendedor, Double>();
    Semaphore mutex1 = new Semaphore(1);
    Semaphore mutex2 = new Semaphore(1);
    Semaphore mutex3 = new Semaphore(1);

    public Reloj(List<Comprador> compradores, List<Local> locales) {
        this.compradores = compradores;
        this.locales = locales;
    }

    public void corre() {
//        System.out.println("corre el reloj");
        try {
            while (compradores.size()!=0) {
//                System.out.println("corre el while del reloj");
                for (Comprador comprador : compradores) {
//                    System.out.println("primer for del while");
                    if (comprador.getTiempoIngreso().compareTo(tiempoTrascurrido)==0) {
                        System.out.println("primer if del while");
//                        mutex1.acquire();
                        for (Local local : locales) {
                            if (local.nombre.equals(comprador.getLocal())) {
                                local.getCompradores().add(comprador);
                                System.out.println("pongo los compradores en los locales");
                            }
                        }
                        compradores.remove(comprador);
//                        mutex1.release();
                    }
                }
                for (Local local : locales) {
                    if (!local.vendedores.isEmpty()) {
                        for (Vendedor vendedorLibre : local.vendedores) {
                            if (!local.getCompradores().isEmpty()) {
                                for (Comprador comprador : local.getCompradores()) {
                                    comprador.start();
//                                    mutex2.acquire();
                                    local.getCompradores().remove(comprador);
                                    vendedoresALiberar.put(comprador.getVendedor(), comprador.getTiempoEnVentanilla() + comprador.getTiempoIngreso());
//                                    mutex2.release();
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
            }
            tiempoTrascurrido += 1.0;
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
