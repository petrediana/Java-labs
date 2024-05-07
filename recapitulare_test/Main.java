import contracts.Reader;
import models.Client;
import models.ClientType;
import models.Product;
import readers.ClientsReaderFromTxt;
import readers.ProductsReaderFromBinary;
import readers.ProductsReaderFromTxt;
import writers.ProductsWriterToBinary;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        String productsPath = "/Users/diana.petre/Documents/facultate/recap_test/data/products.txt";
        String clientsPath = "/Users/diana.petre/Documents/facultate/recap_test/data/clients.txt";

        String productsBinaryPath =
                "/Users/diana.petre/Documents/facultate/recap_test/data/products.dat";

        var productsMap = new ProductsReaderFromTxt()
                .read(productsPath);
        System.out.println(productsMap);

        var clients = new ClientsReaderFromTxt(productsMap)
                .read(clientsPath);
        System.out.println(clients);

//        new ProductsWriterToBinary().write(productsBinaryPath,
//                productsMap.values().stream().toList());
//
//        var productsFromBinaryFile = new ProductsReaderFromBinary().read(productsBinaryPath);
//        System.out.println(productsFromBinaryFile);

        System.out.println(clients.get(2).getPayedSum());

        /**
         * Use the Java stream API for the following:
         * - group the list of clients by the ClientType
         * - filter clients who have more than 5 bought products
         * - map clients to their full name (have a list with the clients full name)
         * - find all unique products of clients*
         *     Extra Note*: Assuming `Product` has a proper `equals` and `hashCode` implemented,
         *     use a flatMap to get the products from the clients list and collect
         *         the results to a `Set` to remove duplicates
         * - find clients that have a specific Product
         * - get the clients with the maximum number of products
         */

        System.out.println("------");
        Map<ClientType, List<Client>> clientsByType = clients.stream()
                .collect(Collectors.groupingBy(Client::getType));
        System.out.println(clientsByType);

        System.out.println("------");
        clients.stream().filter(client -> client.getProducts().size() > 10)
                .forEach(System.out::println);

        System.out.println("------");
        clients.stream()
                .map(client -> client.getSurname() + " " + client.getLastname())
                .forEach(System.out::println);

        System.out.println("------");
        Set<Product> uniqueProducts = clients.stream()
                .flatMap(client -> client.getProducts().stream())
                .collect(Collectors.toSet());
        System.out.println(uniqueProducts);

        System.out.println("------");
        Product specificProduct = productsMap.get(10);
        clients.stream()
                .filter(client -> client.getProducts().contains(specificProduct))
                .forEach(System.out::println);

        System.out.println("------");
        var result = clients.stream()
                .max(Comparator.comparing(client -> client.getProducts().size()));
        System.out.println(result);
    }
}