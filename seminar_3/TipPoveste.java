package seminar_3;

public enum TipPoveste {
    Basm("basm"),
    Actiune("actiune"),
    Drama("drama");

    private final String tipPoveste;

    private TipPoveste(String tipPoveste) {
        this.tipPoveste = tipPoveste;
    }

    public String getTipPoveste() {
        return tipPoveste;
    }
}
