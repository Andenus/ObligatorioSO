import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by user on 24/05/2014.
 */
public class Local {

    List<Vendedor> vendedores;
    List<Vendedor> vendedoresOcupados = new ArrayList<Vendedor>();
    private List<Comprador> compradores = new ArrayList<Comprador>();
    String nombre;
    Semaphore vendedoresLibres; //inicializado en el JSON

    public Local(List<Vendedor> vendedores, String nombre) {
        this.vendedores = vendedores;
        vendedoresLibres = new Semaphore(vendedores.size());
        this.nombre = nombre;
    }

    public Vendedor asignarVendedor() throws InterruptedException{
        vendedoresLibres.acquire();
        Vendedor vendedor = vendedores.get(0);
        vendedoresOcupados.add(vendedor);
        vendedores.remove(0);
        return vendedor;
    }

    public void liberarVendedor(Vendedor vendedor) throws InterruptedException{
        vendedoresOcupados.remove(vendedor);
        vendedores.add(vendedor);
        vendedoresLibres.release();
    }

    public List<Comprador> getCompradores() {
        return compradores;
    }

    public void setCompradores(List<Comprador> compradores) {
        this.compradores = compradores;
    }
}