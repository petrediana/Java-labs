public class Profesor {
    private final int id;
    private final String prenume;
    private final String nume;
    private final String departament;

    public Profesor(int id, String prenume, String nume, String departament) {
        this.id = id;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getId() {
        return id;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }
}
