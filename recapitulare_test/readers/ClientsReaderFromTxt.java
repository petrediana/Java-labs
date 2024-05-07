package readers;

import contracts.Reader;
import models.Client;
import models.ClientType;
import models.Product;

import javax.lang.model.type.NullType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientsReaderFromTxt implements Reader<List<Client>> {
    private final Map<Integer, Product> productMap;

    public ClientsReaderFromTxt(Map<Integer, Product> productMap) {
        this.productMap = productMap;
    }

    @Override
    public List<Client> read(String filePath) throws IOException {
        List<Client> clients = new ArrayList<>();

        try (var buffer = new BufferedReader(new FileReader(filePath))) {
            String line = buffer.readLine();

            do {
                String[] info = line.split(",");
                String surname = info[0];
                String lastname = info[1];
                ClientType type = getTypeFromString(info[2]);

                List<Product> products = new ArrayList<>();
                for (int i = 3; i < info.length; ++i) {
                    int productId = Integer.parseInt(info[i]);
                    products.add(productMap.get(productId));
                }

                clients.add(new Client(surname, lastname, type, products));

                line = buffer.readLine();
            } while (line != null);
        }

        return clients;
    }

    private ClientType getTypeFromString(String type) {
        if (ClientType.New.getType().equals(type)) {
            return ClientType.New;
        }

        if (ClientType.Special.getType().equals(type)) {
            return ClientType.Special;
        }

        if (ClientType.Fidel.getType().equals(type)) {
            return ClientType.Fidel;
        }

        if (ClientType.Exclusive.getType().equals(type)) {
            return ClientType.Exclusive;
        }

        return ClientType.New;
    }
}
