package exemplu_test;

import java.io.*;
import java.util.*;

/**
 * Subiectul 1
 */
class Pacient implements Serializable {
    private final int cod;
    private final String nume;
    private final String sectie;

    public Pacient(int cod, String nume, String sectie) {
        this.cod = cod;
        this.nume = nume;
        this.sectie = sectie;
    }

    public int getCod() {
        return cod;
    }

    public String getNume() {
        return nume;
    }

    public String getSectie() {
        return sectie;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                ", sectie='" + sectie + '\'' +
                '}';
    }
}

public class ExempluTest {
    public static void main(String[] args) throws IOException {
        final String caleFisier = "exemplu_test\\data\\pacienti.txt";

        List<Pacient> pacienti = citesteDinFisierText(caleFisier);
        int numarPacienti = pacienti.size();

        System.out.println("Numarul total de pacienti internati in spital: " + numarPacienti);

        var pacientiDupaSectie = pacientiDupaSectie(pacienti);
        System.out.println(pacientiDupaSectie);

        var pacientiDupaSectieSortati = sortSectiiDupaNumarPacienti(pacientiDupaSectie);
        System.out.println(pacientiDupaSectieSortati);


        // write patient data to binary file
        scrieInBinar(pacienti, "exemplu_test\\data\\pacienti.dat");

        // read and display patient data from binary file
        List<Pacient> pacientiDinBinar = citesteDinBinar("exemplu_test\\data\\pacienti.dat");
        System.out.println("Pacientii din fisier binar: \n" + pacientiDinBinar);
    }

    /**
     * Subiectul 2
     */
    private static List<Pacient> citesteDinFisierText(String caleFisier) throws IOException {
        List<Pacient> pacienti = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(caleFisier));

        String linie;
        while ((linie = reader.readLine()) != null) {
            String[] fields = linie.split(",");
            int cod = Integer.parseInt(fields[0]);
            String nume = fields[1];
            String sectie = fields[2];

            Pacient pacient = new Pacient(cod, nume, sectie);

            pacienti.add(pacient);
        }
        reader.close();

        return pacienti;
    }

    /**
     * Subiectul 3 - Maparea sectiilor pentru pacienti
     */
    private static Map<String, Integer> pacientiDupaSectie(List<Pacient> pacienti) {
        Map<String, Integer> sectiiPacienti = new HashMap<>();

        for (Pacient pacient : pacienti) {
            String sectie = pacient.getSectie();

            int numarPacienti = sectiiPacienti.getOrDefault(sectie, 0);

            sectiiPacienti.put(sectie, numarPacienti + 1);
        }
        return sectiiPacienti;
    }

    /**
     * Subiectul 3 - Punerea map-ului intr-o lista si sortarea ei
     * Alternativa: - se poate crea o alta clasa cu campulurile: sectie si numar pacienti
     *              - transform elementele din map in obiecte de tipul mentionat anterior
     *              - pun elementele intr-o lista si le sortez
     */
    private static List<Map.Entry<String, Integer>> sortSectiiDupaNumarPacienti(Map<String, Integer> sectiiPacienti) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(sectiiPacienti.entrySet());

        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

    /**
     * Subiectul 4 - Scrierea in binar
     */
    private static void scrieInBinar(List<Pacient> pacienti, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            out.writeObject(pacienti);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subiectul 4 - Citirea din binar
     */
    private static List<Pacient> citesteDinBinar(String filename) {
        List<Pacient> pacienti = null;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            pacienti = (List<Pacient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pacienti;
    }
}
