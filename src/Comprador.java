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

    public Comprador(Espectaculo espectaculo, Sistema sistema) {
        this.espectaculo = espectaculo;
        this.sistema = sistema;
    }

    public void run (){
        try {
            System.out.println("Estoy esperando un vendedor");
            Vendedor vendedor = sistema.asignarVendedor();
            System.out.println("Estoy siendo atendido");
            vendedor.vender(zona, cantDeEntradas, espectaculo);
            sistema.liberarVendedor(vendedor);
        } catch (InterruptedException e){

        }
    }
}
