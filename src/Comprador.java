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
    private String local;
    private Semaphore mutex = new Semaphore(1);
    private List<Entrada> asientosAsignados;
    private Double tiempoIngreso;
    private Vendedor vendedor;
    private Double tiempoEnVentanilla;


    public Comprador(int idComprador, String tipoComprador, String zona, int cantDeEntradas, String espectaculo, String local, Double tiempoIngreso) {
        this.idComprador = idComprador;
        this.tipoComprador = tipoComprador;
        this.zona = zona;
        this.cantDeEntradas = cantDeEntradas;
        this.espectaculo = espectaculo;
        this.setLocal(local);
        this.setTiempoIngreso(tiempoIngreso);
        this.setTiempoEnVentanilla(2+(0.08*cantDeEntradas));
    }

    public int getIdComprador() {
        return idComprador;
    }

    public int getCantDeEntradas() {
        return cantDeEntradas;
    }

    public void run (){

//        System.out.println("Comprador:" +idComprador+", aún no ha llegado");

        comprar();
        //VERR SI FUNCIONA DE ACA PODRIAMOS SACAR LOS HILOS QUE SE TIENENE QUE PLANIFICAR Y CORTAR EL RESTO...
        //Esto para este caso solo deberia correr el hilo del comprador 2, y solo una vez...
        //Corre solo el hilo 2 como debe ser, pero corre muchas veces el mismo...
//        while (true)
//            {
//                if (this.getTiempoIngreso() <3)
//                {
//                    System.out.println("Entra"+idComprador);
//                    comprar();
//                }
//            }
    }

    public void  comprar()
    {
        System.out.println("Comprador:" +idComprador+", Esperando vendedor, Local:"+ getLocal()+", Zona:"+zona);

        System.out.println("Comprador:" +idComprador+", Siendo atendido, Local:"+ getLocal()+", Zona:"+zona);
        getVendedor().vender(zona, cantDeEntradas, espectaculo);

        System.out.println("Comprador:" +idComprador+", Compra entradas("+cantDeEntradas+"), Local:"+ getLocal()+", Zona:"+zona);

//        getLocal().liberarVendedor(getVendedor());
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Double getTiempoEnVentanilla() {
        return tiempoEnVentanilla;
    }

    public void setTiempoEnVentanilla(Double tiempoEnVentanilla) {
        this.tiempoEnVentanilla = tiempoEnVentanilla;
    }

    public Double getTiempoIngreso() {
        return tiempoIngreso;
    }

    public void setTiempoIngreso(Double tiempoIngreso) {
        this.tiempoIngreso = tiempoIngreso;
    }
}
