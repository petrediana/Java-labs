package seminar_3;

import java.util.Objects;

public class Carte implements Comparable<Carte>, Cloneable {
    private final String titlu;
    private final TipPoveste tipPoveste;
    private final int anPublicare;

    public Carte(String titlu, TipPoveste tipPoveste, int anPublicare) {
        this.titlu = titlu;
        this.tipPoveste = tipPoveste;
        this.anPublicare = anPublicare;
    }

    public String getTitlu() {
        return titlu;
    }

    public TipPoveste getTipPoveste() {
        return tipPoveste;
    }

    public int getAnPublicare() {
        return anPublicare;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "titlu='" + titlu + '\'' +
                ", tipPoveste=" + tipPoveste +
                ", anPublicare=" + anPublicare +
                '}';
    }

    @Override
    public int compareTo(Carte o) {
        return Integer.compare(this.anPublicare, o.getAnPublicare());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return anPublicare == carte.anPublicare && Objects.equals(titlu, carte.titlu) && tipPoveste == carte.tipPoveste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titlu, tipPoveste, anPublicare);
    }

    @Override
    public Carte clone() {
        try {
            Carte shallowCopy = (Carte) super.clone();
            return new Carte(shallowCopy.getTitlu(), shallowCopy.getTipPoveste(), shallowCopy.getAnPublicare());
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
