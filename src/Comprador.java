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
    private String nombreLocal;
    private Local local;
    private Semaphore mutex = new Semaphore(1);
    private List<Entrada> asientosAsignados;
    private Double tiempoIngreso;
    private Vendedor vendedor;
    private Double tiempoEnVentanilla;

    public Comprador(int idComprador, String tipoComprador, String zona, int cantDeEntradas, String espectaculo, String nombreLocal, Double tiempoIngreso) {
        this.setIdComprador(idComprador);
        this.setTipoComprador(tipoComprador);
        this.setZona(zona);
        this.setCantDeEntradas(cantDeEntradas);
        this.setEspectaculo(espectaculo);
        this.setNombreLocal(nombreLocal);
        this.setTiempoIngreso(tiempoIngreso);
        this.setTiempoEnVentanilla(2+(0.5*cantDeEntradas));
        if (this.getCantDeEntradas()>=50 && !this.getTipoComprador().equals("P")){
            this.setTipoComprador("GC");
        }
    }

    public int getIdComprador() {
        return idComprador;
    }

    public int getCantDeEntradas() {
        return cantDeEntradas;
    }

    public void run () {
        try {
            setVendedor(getLocal().asignarVendedor());
            local.getCompradores().remove(this);
            local.getCompradoresSiendoAtendidos().add(this);
            comprar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void  comprar() {

        System.out.println("Comprador:" + getIdComprador() +", Esperando vendedor, Local:"+ getNombreLocal()+", Zona:"+ getZona());

        System.out.println("Comprador:" + getIdComprador() +", Siendo atendido, Local:"+ getNombreLocal()+", Zona:"+ getZona());
        asientosAsignados = getVendedor().vender(getZona(), getCantDeEntradas(), getEspectaculo());

        System.out.println("Comprador:" + getIdComprador() +", Compra entradas("+ getCantDeEntradas() +"), Local:"+ getNombreLocal()+", Zona:"+ getZona());
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
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

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public String getTipoComprador() {
        return tipoComprador;
    }

    public void setTipoComprador(String tipoComprador) {
        this.tipoComprador = tipoComprador;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setCantDeEntradas(int cantDeEntradas) {
        this.cantDeEntradas = cantDeEntradas;
    }

    public String getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(String espectaculo) {
        this.espectaculo = espectaculo;
    }

    public List<Entrada> getAsientosAsignados() {
        return asientosAsignados;
    }

    public void setAsientosAsignados(List<Entrada> asientosAsignados) {
        this.asientosAsignados = asientosAsignados;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
