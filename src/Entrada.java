/**
 * Created by user on 24/05/2014.
 */
public class Entrada {
    private int numAsiento;
    private String zona;
    private int precio;
    private boolean libre = true;

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Entrada(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public int getPrecio() {
        return precio;
    }
}
