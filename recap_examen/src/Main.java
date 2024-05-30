import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        String reviewsFilePath = "data\\reviews.txt";
        String productsFilePath = "data\\products.txt";

        var reviews = readFromTxt(reviewsFilePath);
        var products = readProductsFromTxt(productsFilePath, reviews);

        System.out.println(reviews);
        System.out.println(products);

        Review review1 = new Review(1, "Asda", "Asda s", 10);
        Review review2 = new Review(1, "Asda", "Asda s", 10);
        Review review3 = new Review(1, "Asda", "Asda s", 10);

        if (review1.equals(review2)) {
            System.out.println("Au prop egale!");
        }

        var reviewsList = reviews.values();

        reviewsList.stream().filter(review -> review.getRating() > 7)
                .forEach(System.out::println);

        reviewsList.stream().map(review -> review.getClientName())
                .forEach(System.out::println);

        reviewsList.stream().sorted(Comparator.comparingDouble(Review::getRating))
                .forEach(System.out::println);

        OptionalDouble average = reviewsList.stream().mapToDouble(Review::getRating).average();
        System.out.println(average);

        OptionalDouble max = reviewsList.stream().mapToDouble(Review::getRating).max();
        System.out.println(max);

        reviewsList.stream().filter(review -> review.getRating() == max.getAsDouble())
                .forEach(System.out::println);

        String dbUrl = "jdbc:sqlite:data\\database.db";

        var dbHelper = new DbHelper(dbUrl);
        dbHelper.prepareTable();
        dbHelper.add(new ArrayList<>(reviewsList));


        System.out.println("------------------");
        System.out.println(dbHelper.getFromTable());

        dbHelper.delete(1);
        System.out.println(dbHelper.getFromTable());

        dbHelper.update(2, new Review(2, "sidhfjsidu", "dsfiodj", 0));
        System.out.println(dbHelper.getFromTable());

        String reviewsBinaryPath = "data\\reviews.dat";
        writeReviewsToBinary(reviewsBinaryPath, new ArrayList<>(reviewsList));
        System.out.println(
                readReviewsFromBinary(reviewsBinaryPath)
        );

    }

    private static void writeReviewsToBinary(String filePath, List<Review> reviews) throws Exception {
        try (
                var dataOuputStream = new DataOutputStream(new BufferedOutputStream(
                        new FileOutputStream(filePath)))
                ) {
            for (var review : reviews) {
                dataOuputStream.writeInt(review.getId());
                dataOuputStream.writeUTF(review.getClientName());
                dataOuputStream.writeUTF(review.getDescription());
                dataOuputStream.writeDouble(review.getRating());
            }
        }
    }

    private static List<Review> readReviewsFromBinary(String filePath) throws Exception {
        List<Review> reviews = new ArrayList<>();

        try (var dataInputStream = new DataInputStream(new BufferedInputStream(
                new FileInputStream(filePath)))) {
            while (true) {
                int id = dataInputStream.readInt();
                String clientName = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                double rating = dataInputStream.readDouble();

                reviews.add(new Review(id, clientName, description, rating));
            }
        } catch (EOFException exception) {
            System.out.println("End of the binary file reached!");
        }

        return reviews;
    }

    private static Map<Integer, Review> readFromTxt(String filePath) throws Exception {
        try (var buffer = new BufferedReader(new FileReader(filePath))) {
            return buffer
                    .lines()
                    .map(line -> {
                        String[] properties = line.split(",");
                        int id = Integer.parseInt(properties[0]);
                        String name = properties[1];
                        String description = properties[2];
                        double rating = Double.parseDouble(properties[3]);

                        return new Review(id, name, description, rating);
                    })
                    .collect(Collectors.toMap(Review::getId, r -> r));
        }
    }

    private static List<Product> readProductsFromTxt(String filePath,
                                                     Map<Integer, Review> reviewsMap) throws Exception {
        List<Product> products = new ArrayList<>();

        try (var buffer = new BufferedReader(new FileReader(filePath))) {
            String line = buffer.readLine();

            do {
                String[] properties = line.split(",");
                String name = properties[0];
                double price = Double.parseDouble(properties[1]);

                List<Review> reviews = new ArrayList<>();
                for (int i = 2; i < properties.length; ++i) {
                    int reviewId = Integer.parseInt(properties[i]);
                    reviews.add(reviewsMap.get(reviewId));
                }

                Product product = new Product(name, price, reviews);
                products.add(product);

                line = buffer.readLine();
            } while (line != null);
        }

        return products;
    }
}
