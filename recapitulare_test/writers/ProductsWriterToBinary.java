package writers;

import contracts.Writer;
import models.Product;

import java.io.*;
import java.util.List;

public class ProductsWriterToBinary implements Writer<List<Product>> {
    @Override
    public void write(String filePath, List<Product> data) throws IOException {
        try (var dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filePath)
                )
        )) {
            for (Product product : data) {
                dataOutputStream.writeInt(product.getId());
                dataOutputStream.writeUTF(product.getName());
                dataOutputStream.writeInt(product.getQuantity());
                dataOutputStream.writeDouble(product.getPrice());
            }
        }
    }
}
