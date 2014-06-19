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
    private int tiempoIngreso;


    public Comprador(int idComprador, String tipoComprador, String zona, int cantDeEntradas, String espectaculo, Local local, int tiempoIngreso) {
        this.idComprador = idComprador;
        this.tipoComprador = tipoComprador;
        this.zona = zona;
        this.cantDeEntradas = cantDeEntradas;
        this.espectaculo = espectaculo;
        this.local = local;
        this.tiempoIngreso=tiempoIngreso;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public int getCantDeEntradas() {
        return cantDeEntradas;
    }

    public void run (){

        System.out.println("Comprador:" +idComprador+", aún no ha llegado");

        //VERR SI FUNCIONA DE ACA PODRIAMOS SACAR LOS HILOS QUE SE TIENENE QUE PLANIFICAR Y CORTAR EL RESTO...
        //Esto para este caso solo deberia correr el hilo del comprador 2, y solo una vez...
        //Corre solo el hilo 2 como debe ser, pero corre muchas veces el mismo...
        while (true)
            {
                if (this.tiempoIngreso<3)
                {
                    System.out.println("Entra"+idComprador);
                    comprar();
                }
            }
    }

    public void  comprar()
    {
        try {
        System.out.println("Comprador:" +idComprador+", Esperando vendedor, Local:"+local.nombre+", Zona:"+zona);
        Vendedor vendedor = local.asignarVendedor();

        System.out.println("Comprador:" +idComprador+", Siendo atendido, Local:"+local.nombre+", Zona:"+zona);
        vendedor.vender(zona, cantDeEntradas, espectaculo);

        System.out.println("Comprador:" +idComprador+", Compra entradas("+cantDeEntradas+"), Local:"+local.nombre+", Zona:"+zona);

        local.liberarVendedor(vendedor);

    }catch (InterruptedException e){
        System.out.println("BOOOM!!");
        e.printStackTrace();
    }
 }
}
