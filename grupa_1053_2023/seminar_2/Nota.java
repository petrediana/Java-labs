package eu.diana.src;

public class Nota {
    private String materie;
    private int punctaj;

    public Nota(String materie, int punctaj) {
        this.materie = materie;
        this.punctaj = punctaj;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        if (punctaj <= 0) {
            throw new IllegalArgumentException("Punctajul nu poate fi mai mic sau egal cu 0!");
        }
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return String.format("Materia: %s, punctaj: %d", this.materie, this.punctaj);
    }
}
