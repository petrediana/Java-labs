import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    private final String dbUrl;
    private final Connection connection;

    public DbHelper(String dbUrl) throws SQLException {
        this.dbUrl = dbUrl;

        this.connection = DriverManager.getConnection(this.dbUrl);
    }

    public void prepareTable() {
        try (
                var command = this.connection.createStatement();
                ) {

            command.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS REVIEWS(" +
                            "ID INTEGER, " +
                            "ClientName String(100), " +
                            "Description String(100), " +
                            "Rating REAL)"
            );

            command.executeUpdate("DELETE FROM REVIEWS");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(List<Review> reviews) {
        try (
                var command = this.connection.prepareStatement(
                        "INSERT INTO REVIEWS VALUES (?, ?, ?, ?)")
                ) {
            for (var review : reviews) {
                command.setInt(1, review.getId());
                command.setString(2, review.getClientName());
                command.setString(3, review.getDescription());
                command.setDouble(4, review.getRating());

                command.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getFromTable() {
        List<Review> reviews = new ArrayList<>();

        try (
                var select = this.connection.createStatement();
                var cursor = select.executeQuery("SELECT * FROM REVIEWS")
                ) {
            while(cursor.next()) {
                int id = cursor.getInt("ID");
                String clientName = cursor.getString("ClientName");
                String description = cursor.getString("Description");
                double rating = cursor.getDouble("Rating");

                reviews.add(new Review(id, clientName, description, rating));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return reviews;
    }

    public void delete(int id) {
        try (
                var command = this.connection.prepareStatement(
                        "DELETE FROM REVIEWS WHERE ID=" + id
                )
                ) {
            command.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Review review) {
        try (
                var command = this.connection.prepareStatement(
                        "UPDATE REVIEWS SET ClientName = ?, Description = ?, Rating = ?" +
                                "WHERE ID = ?"
                )
                ) {
            command.setString(1, review.getClientName());
            command.setString(2, review.getDescription());
            command.setDouble(3, review.getRating());
            command.setInt(4, id);

            command.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
