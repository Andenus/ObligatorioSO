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
    private String espectaculo;
    private Local local;
    private Semaphore mutex = new Semaphore(1);
    private List<Entrada> asientosAsignados;


    public Comprador(int idComprador, String tipoComprador, String zona, int cantDeEntradas, String espectaculo, Local local) {
        this.idComprador = idComprador;
        this.tipoComprador = tipoComprador;
        this.zona = zona;
        this.cantDeEntradas = cantDeEntradas;
        this.espectaculo = espectaculo;
        this.local = local;
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
            Vendedor vendedor = local.asignarVendedor();

            System.out.println("El comprador número " + getIdComprador() + " está siendo atendido.");
            vendedor.vender(zona, cantDeEntradas, espectaculo);

            System.out.println("El comprador número " + getIdComprador() + " se lleva " + getCantDeEntradas() + " entradas de la" + zona + ".");

            local.liberarVendedor(vendedor);

        }catch (InterruptedException e){
            System.out.println("BOOOM!!");
            e.printStackTrace();
        }
    }
}
