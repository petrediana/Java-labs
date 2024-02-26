import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static void afisare(List<Persoana> persoane) {
        for (Persoana persoana : persoane) {
            System.out.println(persoana);
        }
    }

    private static void scrieBinar(String caleFisier, List<Persoana> persoane)
            throws IOException {

        if (new File(caleFisier).getParent() != null) {
            new File(caleFisier).getParentFile().mkdir();
        }

        try (var fisier = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(caleFisier)
                )
        )) {
            fisier.writeObject(persoane);
        }
    }

    private static List<Persoana> citesteDinBinar(String caleFisier)
            throws IOException, ClassNotFoundException {

        try (var fisier = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(caleFisier)
                )
        )) {
            return (List<Persoana>) fisier.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String caleFisier = "data\\persoane.dat";
        List<Persoana> persoane = List.of(
                new Persoana(10, "Nume-Prenume-1", 10),
                new Persoana(11, "Nume-Prenume-2", 11),
                new Persoana(12, "Nume-Prenume-3", 13),
                new Persoana(13, "Nume-Prenume-4", 14),
                new Persoana(14, "Nume-Prenume-5", 15),
                new Persoana(15, "Nume-Prenume-6", 16),
                new Persoana(16, "Nume-Prenume-7", 17),
                new Persoana(17, "Nume-Prenume-8", 18)
        );

        afisare(persoane);

        scrieBinar(caleFisier, persoane);

        List<Persoana> persoaneCitite = citesteDinBinar(caleFisier);

        System.out.println("----- Citire din fisier binar ----");
        afisare(persoaneCitite);

        // forEach, filter, map

        System.out.println("-------------------------------");

        // forEach
        // Mod functional
        persoaneCitite.stream().forEach(persoana -> {
            System.out.println(persoana);
        });

        // Imperatriv
        for (int i = 0; i < persoaneCitite.size(); ++i) {
            System.out.println(persoaneCitite.get(i));
        }

        System.out.println("-------------------------------");

        // filter
        var persoaneFiltrate = persoaneCitite.stream().filter(persoana -> {
            return persoana.getId() % 2 == 0;
        }).collect(Collectors.toList());

        System.out.println(persoaneFiltrate);

        System.out.println("-------------------------------");
        // map
        var persoaneMapate = persoaneCitite.stream().map(persoana -> {
            final int id = persoana.getId() + 100;

           return new Persoana(id, persoana.getNume(), persoana.getVarsta());
        }).collect(Collectors.toList());

        System.out.println(persoaneMapate);


        persoaneCitite.stream().forEach(System.out::println);
        
        persoaneCitite.stream().filter(Persoana::esteMajor);
    }
}