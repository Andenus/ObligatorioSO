import java.rmi.Naming;
import java.util.concurrent.Semaphore;

/**
 * Created by user on 24/05/2014.
 */
public class Comprador extends Thread {
    int idComprador;
    String tipoComprador;
    Zona zona;
    int cantDeEntradas;
    Espectaculo espectaculo;
    Sistema sistema;
    Semaphore mutex = new Semaphore(1);

    public Comprador(int idComprador, String tipoComprador, Zona zona, int cantDeEntradas, Espectaculo espectaculo, Sistema sistema) {
        this.idComprador = idComprador;
        this.tipoComprador = tipoComprador;
        this.zona = zona;
        this.cantDeEntradas = cantDeEntradas;
        this.espectaculo = espectaculo;
        this.sistema = sistema;
    }

    public void run (){
        try {
            System.out.println("Estoy esperando un vendedor");
            Vendedor vendedor = sistema.asignarVendedor();
            System.out.println("Estoy siendo atendido");
            mutex.acquire();
            vendedor.vender(zona, cantDeEntradas, espectaculo);
            mutex.release();
            sistema.liberarVendedor(vendedor);
        } catch (InterruptedException e){

        }
    }
}
