import java.sql.SQLException;
import java.util.List;

public class Main {

    static final String dbURL = "jdbc:sqlite:data\\products.db";

    public static void main(String[] args)
    {
        var db = new DbHelper(dbURL);

        List<Product> products = List.of(
                new Product(1, "asdasd", 10.10),
                new Product(2, "asdasd", 11.10),
                new Product(3, "asdasd", 12.10),
                new Product(4, "asdasd", 13.10),
                new Product(5, "asdasd", 14.10)
        );

        try {
            db.createTable();

            db.add(products);

            System.out.println(db.get());

            db.delete(1);
            db.delete(2);
            db.delete(3);

            System.out.println(db.get());

            db.update(4, new Product(4, "NUME_4", 100.100));
            db.update(5, new Product(5, "NUME_5", 2.02));

            System.out.println(db.get());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}