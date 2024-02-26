public class Departament {
    private String denumire;
    private int numarProfesori;

    public Departament(String denumire, int numarProfesori) {
        this.denumire = denumire;
        this.numarProfesori = numarProfesori;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarProfesori() {
        return numarProfesori;
    }

    public void setNumarProfesori(int numarProfesori) {
        this.numarProfesori = numarProfesori;
    }

    @Override
    public String toString() {
        return "Departament{" +
                "denumire='" + denumire + '\'' +
                ", numarProfesori=" + numarProfesori +
                '}';
    }
}
