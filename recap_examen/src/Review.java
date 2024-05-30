import java.util.Comparator;
import java.util.Objects;

public class Review implements Cloneable {
    private final int id;
    private final String clientName;
    private final String description;
    private final double rating;

    public Review(int id, String clientName, String description, double rating) {
        this.id = id;
        this.clientName = clientName;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public Review clone() {
        try {
            Review clone = (Review) super.clone();

            Review deepCopy = new Review(clone.getId(), clone.getClientName(),
                    clone.getDescription(), clone.getRating());


            return deepCopy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id
                && Double.compare(rating, review.rating) == 0
                && Objects.equals(clientName, review.clientName)
                && Objects.equals(description, review.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, description, rating);
    }
}
