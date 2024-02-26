package seminar_1;

public class Student {
    private int varsta;
    private String nume;
    private Nota[] note;

    public Student(int varsta, String nume, Nota[] note) {
        this.varsta = varsta;
        this.nume = nume;

        // Deep copy!
        this.note = new Nota[note.length];
        for (int i = 0; i < this.note.length; ++i) {
            this.note[i] = new Nota(note[i].getMedie(), note[i].getMaterie());
        }
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Nota[] getNote() {
        return note;
    }

    public void setNote(Nota[] note) {
        this.note = note;
    }

    public void afisareNote() {
        for (int i = 0; i < this.note.length; ++i) {
            System.out.println(this.note[i]);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "varsta=" + varsta +
                ", nume='" + nume + '\'' +
                '}';
    }
}
