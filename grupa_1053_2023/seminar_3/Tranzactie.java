import java.time.LocalDate;

public class Tranzactie {
    private int codProdus;

    private TipTranzactie tipTranzactie;

    private LocalDate data;

    public Tranzactie(int codProdus, TipTranzactie tipTranzactie, LocalDate data) {
        this.codProdus = codProdus;
        this.tipTranzactie = tipTranzactie;
        this.data = data;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public TipTranzactie getTipTranzactie() {
        return tipTranzactie;
    }

    public void setTipTranzactie(TipTranzactie tipTranzactie) {
        this.tipTranzactie = tipTranzactie;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "codProdus=" + codProdus +
                ", tipTranzactie=" + tipTranzactie +
                ", data=" + data +
                '}';
    }
}
