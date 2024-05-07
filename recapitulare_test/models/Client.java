package models;

import java.util.List;
import java.util.Objects;

public class Client {
    private final String surname;
    private final String lastname;
    private final ClientType type;
    private final List<Product> products;

    public Client(String surname, String lastname, ClientType type, List<Product> products) {
        this.surname = surname;
        this.lastname = lastname;
        this.type = type;
        this.products = products;
    }

    public double getPayedSum() {
        double sum = 0;

        for (Product product : products) {
            sum += product.getTotalPrice();
        }

        if (type == ClientType.Special) {
            if (products.size() > 10) {
                sum -= 100;
            }
        } else if (type == ClientType.Fidel) {
            if (products.size() > 8) {
                sum -= 200;
            }
        } else if (type == ClientType.Exclusive) {
            if (products.size() > 5) {
                sum -= 300;
            }
        }

        return sum;

//        return new ClientTypeSpecialHandler().handle(sum, products.size());
    }

    public String getSurname() {
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public ClientType getType() {
        return type;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(surname, client.surname) && Objects.equals(lastname, client.lastname) && type == client.type && Objects.equals(products, client.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, lastname, type, products);
    }

    @Override
    public String toString() {
        return "Client{" +
                "surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", type=" + type +
                ", products=" + products +
                '}';
    }
}
