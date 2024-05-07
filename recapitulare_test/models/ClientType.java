package models;

public enum ClientType {
    New("nou"),
    Special("special"),
    Fidel("fidel"),
    Exclusive("exclusiv");

    private final String type;

    private ClientType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
