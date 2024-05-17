import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    private final String dbUrl;

    public DbHelper(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void createTable() throws SQLException {
        try (
                var connection = DriverManager.getConnection(dbUrl);
                var statement = connection.createStatement();
                ) {

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS PRODUCTS (" +
                            "ID INTEGER, " +
                            "NAME STRING(100), " +
                            "PRICE REAL)"
            );

            statement.executeUpdate("DELETE FROM PRODUCTS");
        }
    }

    public void add(List<Product> products) throws SQLException {
        try (
                var connection = DriverManager.getConnection(dbUrl);
                var statement = connection.prepareStatement(
                        "INSERT INTO PRODUCTS VALUES (?, ?, ?)"
                )
                ) {

            for (var product : products) {
                statement.setInt(1, product.getId());
                statement.setString(2, product.getName());
                statement.setDouble(3, product.getPrice());

                statement.execute();
            }
        }
    }

    public List<Product> get() throws SQLException {
        List<Product> products = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection(dbUrl);
                var select = connection.createStatement();
                var cursor = select.executeQuery("SELECT * FROM PRODUCTS")
                ) {
            while (cursor.next()) {
                int id = cursor.getInt("ID");
                String name = cursor.getString("NAME");
                double price = cursor.getDouble("PRICE");

                products.add(new Product(id, name, price));
            }
        }

        return products;
    }

    public void delete(int id) throws SQLException {
        try (
                var connection = DriverManager.getConnection(dbUrl);
                var statement = connection.prepareStatement(
                        "DELETE FROM PRODUCTS WHERE ID=" + id
                )
                ) {
            statement.execute();
        }
    }

    public void update(int id, Product newValue) throws SQLException {
        try (
                var connection = DriverManager.getConnection(dbUrl);
                var statement = connection.prepareStatement(
                        "UPDATE PRODUCTS SET NAME=?, PRICE=? WHERE ID=" + id
                )
                ) {

            statement.setString(1, newValue.getName());
            statement.setDouble(2, newValue.getPrice());

            statement.execute();
        }
    }
}
