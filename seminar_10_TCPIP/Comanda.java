import java.io.Serializable;

public class Comanda implements Serializable {
    public String denumire;
    public Object parametru;

    public Comanda(String denumire) {
        this.denumire = denumire;
        this.parametru = null;
    }

    public Comanda(String denumire, Object parametru) {
        this.denumire = denumire;
        this.parametru = parametru;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comanda{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", parametru=").append(parametru);
        sb.append('}');
        return sb.toString();
    }
}
