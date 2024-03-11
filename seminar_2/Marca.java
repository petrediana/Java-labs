public enum Marca {
    Dacia("dacia"),
    Ford("ford"),
    Suzuki("suzuki");

    private final String denumire;

    private Marca(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return this.denumire;
    }
}
