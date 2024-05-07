package readers;

import contracts.Reader;
import models.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsReaderFromTxt implements Reader<Map<Integer, Product>> {
    @Override
    public Map<Integer, Product> read(String filePath) throws IOException {
        try (var buffer = new BufferedReader(new FileReader(filePath))) {
            return buffer
                    .lines()
                    .map(ProductsReaderFromTxt::getProduct)
                    .collect(Collectors.toMap(Product::getId, p -> p));
        }
    }

    private static Product getProduct(String line) {
        String[] info = line.split(",");
        int id = Integer.parseInt(info[0]);
        String name = info[1];
        int quantity = Integer.parseInt(info[2]);
        double price = Double.parseDouble(info[3]);

        return new Product(id, name, quantity, price);
    }
}