import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Produs produs1 = new Produs("mere", 5.05d, 10);
        Produs produs2 = new Produs("pere", 1.05d, 11);
        Produs produs3 = new Produs("gutui", 2.05d, 12);
        Produs produs4 = new Produs("prune", 3.05d, 13);
        Produs produs5 = new Produs("caise", 2.05d, 14);

        Factura factura = new Factura("Client-1", LocalDate.now());
        factura.adaugaProdus(produs1);
        factura.adaugaProdus(produs2);
        factura.adaugaProdus(produs3);
        factura.adaugaProdus(produs4);
        factura.adaugaProdus(produs5);

        Factura factura2 = new Factura("Client-2", LocalDate.of(2024, 3, 15));
        factura2.adaugaProdus(produs1);
        factura2.adaugaProdus(produs5);

        System.out.println(factura);
        System.out.println(factura2);

        List<Factura> facturi = List.of(factura2, factura);
        String caleFisier = "date\\facturi.dat";

        salveazaFacturiInBinar(facturi, caleFisier);

        List<Factura> facturiCititeDinBinar = citesteFacturiDinBinar(caleFisier);
        System.out.println(facturiCititeDinBinar);
    }

    private static void salveazaFacturiInBinar(List<Factura> facturi, String caleFisier) throws IOException {
        if (new File(caleFisier).getParentFile() != null) {
            new File(caleFisier).getParentFile().mkdirs();
        }

//        FileOutputStream fileOutputStream = new FileOutputStream(caleFisier);
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

        try (DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(caleFisier))
        )) {
            for (Factura factura : facturi) {
                dataOutputStream.writeUTF(factura.getDenumireClient());

                dataOutputStream.writeInt(factura.getDataEmitere().getYear());
                dataOutputStream.writeInt(factura.getDataEmitere().getMonthValue());
                dataOutputStream.writeInt(factura.getDataEmitere().getDayOfMonth());

                dataOutputStream.writeInt(factura.getNumarProduse());

                for (int i = 0; i < factura.getNumarProduse(); ++i) {
                    Produs produs = factura.getProdusLaIndex(i);

                    dataOutputStream.writeUTF(produs.getDenumire());
                    dataOutputStream.writeDouble(produs.getPret());
                    dataOutputStream.writeInt(produs.getCantitate());
                }
            }
        }
    }

    private static List<Factura> citesteFacturiDinBinar(String caleFisier) throws IOException {
        List<Factura> facturi = new ArrayList<>();

        try (DataInputStream dataInputStream = new DataInputStream(
                new BufferedInputStream(new FileInputStream(caleFisier))
        )) {
            while (true) {
                String denumireClient = dataInputStream.readUTF();
                int an = dataInputStream.readInt(), luna = dataInputStream.readInt(), zi = dataInputStream.readInt();

                Factura factura = new Factura(denumireClient, LocalDate.of(an, luna, zi));

                int numarProduse = dataInputStream.readInt();
                for (int i = 0; i < numarProduse; ++i) {
                    String denumireProdus = dataInputStream.readUTF();
                    double pret = dataInputStream.readDouble();
                    int cantitate = dataInputStream.readInt();

                    factura.adaugaProdus(denumireProdus, pret, cantitate);
                }

                facturi.add(factura);
            }
        } catch (EOFException exception) {
            System.out.println("Am ajuns la sfarsitul fisierului!");
        }

        return facturi;
    }
}