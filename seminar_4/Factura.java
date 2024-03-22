import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Factura {
    private final String denumireClient;
    private final LocalDate dataEmitere;
    private final List<Produs> produse;

    public Factura(String denumireClient, LocalDate dataEmitere) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;

        this.produse = new ArrayList<>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }

    public void adaugaProdus(Produs produs) {
        if (produs == null) {
            throw new IllegalArgumentException("Produsul este null!");
        }
        this.produse.add(produs);
    }

    public void adaugaProdus(String denumire, double pret, int cantitate) {
        this.produse.add(new Produs(denumire, pret, cantitate));
    }

    public int getNumarProduse() {
        return this.produse.size();
    }

    public Produs getProdusLaIndex(int index) {
        if (index < 0 || index > this.produse.size()) {
            throw new IndexOutOfBoundsException("Index incorect!");
        }

        // this.produse[index]
        return this.produse.get(index);
    }

    public List<Produs> getlista() {
        return this.produse;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "denumireClient='" + denumireClient + '\'' +
                ", dataEmitere=" + dataEmitere +
                ", produse=" + produse +
                '}';
    }
}
