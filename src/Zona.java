/**
 * Created by user on 24/05/2014.
 */
public class Zona {
    private String nombre;
    private int largoFila;
    private int cantFilas;
    private int precioEntrada;

    public int getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(int precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLargoFila() {
        return largoFila;
    }

    public void setLargoFila(int largoFila) {
        this.largoFila = largoFila;
    }

    public int getCantFilas() {
        return cantFilas;
    }

    public void setCantFilas(int cantFilas) {
        this.cantFilas = cantFilas;
    }

    public Zona(String nombre, int cantFilas, int largoFila) {
        this.nombre = nombre;
        this.cantFilas = cantFilas;
        this.largoFila = largoFila;
    }
}
