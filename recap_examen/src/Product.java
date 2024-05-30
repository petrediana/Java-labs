import java.util.ArrayList;
import java.util.List;

public class Product implements Cloneable {
    private final String name;
    private final double price;
    private final List<Review> reviews;

    public Product(String name, double price, List<Review> reviews) {
        this.name = name;
        this.price = price;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public Product clone() {
        try {
            Product shallow = (Product) super.clone();

            List<Review> deepReviews = new ArrayList<>();
            for (Review review : shallow.getReviews()) {
                deepReviews.add(review.clone());
            }

            Product deep = new Product(shallow.getName(), shallow.getPrice(),
                    deepReviews);

            return deep;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
