/**
 * Created by user on 24/05/2014.
 */
public class Asiento {
    private Integer numero;
    private boolean libre = true;

    public Integer getNumero() {
        return numero;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public Asiento(Integer numero) {
        this.numero = numero;
    }
}
