package seminar_1;

public class Nota {
    private final int medie;
    private final String materie;

    public Nota(int medie, String materie) {
        this.medie = medie;
        this.materie = materie;
    }

    public int getMedie() {
        return medie;
    }

    public String getMaterie() {
        return materie;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "medie=" + medie +
                ", materie='" + materie + '\'' +
                '}';
    }
}
