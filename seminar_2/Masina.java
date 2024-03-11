public class Masina {
    private final int id;
    private final String model;
    private final Marca marca;
    private int anFabricare;

    public void setAnFabricare(int an) {
        this.anFabricare = an;
    }

    public Masina(int id, String model, Marca marca, int anFabricare) {
        if (id <= 0) {
            throw new IllegalArgumentException
                    ("Id-ul nu poate fi mai mic sau egal de 0");
        }

        if (anFabricare <= 2015) {
            throw new IllegalArgumentException("Anul de fabricare nu poate fi" +
                    "mai mic sau egal de 2015!");
        }

        this.id = id;
        this.model = model;
        this.marca = marca;
        this.anFabricare = anFabricare;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Marca getMarca() {
        return marca;
    }

    public int getAnFabricare() {
        return anFabricare;
    }

    @Override
    public String toString() {
        return "Masina{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", marca=" + marca +
                ", anFabricare=" + anFabricare +
                '}';
    }
}
