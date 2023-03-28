import java.io.Serializable;

public class Persoana implements Serializable {
    private final int id;
    private final String nume;

    private final int varsta;

    public Persoana(int id, String nume, int varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
    }

    public boolean esteMajor() {
        return varsta > 18;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                '}';
    }
}
