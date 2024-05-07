package readers;

import contracts.Reader;
import models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsReaderFromBinary implements Reader<List<Product>> {
    @Override
    public List<Product> read(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();

        try (var dataInputStream = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(filePath)
                )
        )) {
            while (true) {
                int id = dataInputStream.readInt();
                String name = dataInputStream.readUTF();
                int quantity = dataInputStream.readInt();
                double price = dataInputStream.readDouble();

                products.add(new Product(id, name, quantity, price));
            }
        } catch (EOFException exception) {
            System.out.println("End of file for products!");
        }

        return products;
    }
}
