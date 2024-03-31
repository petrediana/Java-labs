package seminar_5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Hobby hobby1 = new Hobby("Cantat chitara", 10);
        System.out.println(hobby1);

        Person person1 = new Person("name", 25);
        person1.addHobby(hobby1);

        System.out.println(person1);

        final String caleData = "seminar_5\\date-5.txt";

        var persoane = getListaPersoaneDinText(caleData);
        System.out.println(persoane);

        final String calePentruBinar = "binar\\date.dat";
        scriePersoaneInBinar(persoane, calePentruBinar);

        var persoaneDinBinar = citestePersoaneDinBinar(calePentruBinar);
        System.out.println(persoaneDinBinar);
    }

    private static void scriePersoaneInBinar(List<Person> persoane, String caleFisier) throws IOException {
        if (new File(caleFisier).getParent() != null) {
            new File(caleFisier).getParentFile().mkdir();
        }

        try (var objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(caleFisier)
                )
        )) {
            objectOutputStream.writeObject(persoane);
        }
    }

    private static List<Person> citestePersoaneDinBinar(String caleFisier) throws IOException, ClassNotFoundException {
        try (var objectInputStream = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(caleFisier)
                )
        )) {
            return (List<Person>) objectInputStream.readObject();
        }
    }

    private static List<Person> getListaPersoaneDinText(String caleFisier) throws IOException {
        List<Person> persoane = new ArrayList<>();

        try (var buffer = new BufferedReader(new FileReader(caleFisier))) {
//            buffer.lines().forEach(linie -> System.out.println(linie));

            return buffer.lines().map(linie -> {
                String[] linieSplitted = linie.split(",");
                String nume = linieSplitted[0];
                int varsta = Integer.parseInt(linieSplitted[1]);

                Person person = new Person(nume, varsta);

                int nrHobbies = Integer.parseInt(linieSplitted[2]);
                for (int i = 0; i < nrHobbies; ++i) {
                    String numeHobby = linieSplitted[3 + (i * 2)];
                    int likenessHobby = Integer.parseInt(linieSplitted[3 + 1 + (i * 2)]);

                    person.addHobby(new Hobby(numeHobby, likenessHobby));
                }

                return person;
            }).collect(Collectors.toList());


//            String linie = buffer.readLine();
//            do {
//                String[] linieSplitted = linie.split(",");
//                String nume = linieSplitted[0];
//                int varsta = Integer.parseInt(linieSplitted[1]);
//
//                Person person = new Person(nume, varsta);
//
//                int nrHobbies = Integer.parseInt(linieSplitted[2]);
//                for (int i = 0; i < nrHobbies; ++i) {
//                    String numeHobby = linieSplitted[3 + (i * 2)];
//                    int likenessHobby = Integer.parseInt(linieSplitted[3 + 1 + (i * 2)]);
//
//                    person.addHobby(new Hobby(numeHobby, likenessHobby));
//                }
//
//                persoane.add(person);
//
//                linie = buffer.readLine();
//            } while (linie != null);

        }

//        return persoane;
    }
}
