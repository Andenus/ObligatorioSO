import java.rmi.Naming;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by user on 24/05/2014.
 */
public class Comprador extends Thread {

    private int idComprador;
    private String tipoComprador;
    private String zona;
    private int cantDeEntradas;
    private Espectaculo espectaculo;
    private Sistema sistema;
    private Semaphore mutex = new Semaphore(1);
    private List<Asiento> asientosAsignados;


    public Comprador(int idComprador, String tipoComprador, String zona, int cantDeEntradas, Espectaculo espectaculo, Sistema sistema) {
        this.idComprador = idComprador;
        this.tipoComprador = tipoComprador;
        this.zona = zona;
        this.cantDeEntradas = cantDeEntradas;
        this.espectaculo = espectaculo;
        this.sistema = sistema;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public int getCantDeEntradas() {
        return cantDeEntradas;
    }

    public void run (){

        try {
            System.out.println("El comprador número " + getIdComprador() + " está esperando vendedor.");
            Vendedor vendedor = sistema.asignarVendedor();

            mutex.acquire();
            vendedor.vender(zona, cantDeEntradas, espectaculo);
            System.out.println("El comprador número " + getIdComprador() + " está siendo atendido.");
            mutex.release();
            System.out.println("El comprador número " + getIdComprador() + " se lleva " + getCantDeEntradas() + " entradas.");

            sistema.liberarVendedor(vendedor);

        }catch (InterruptedException e){
            System.out.println("BOOOM!!");
            e.printStackTrace();
        }
    }
}
