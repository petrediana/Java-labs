import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private final static String dataProfesori = "/Users/diana.petre/Documents/facultate/seminar_6/src/date/profesori.txt";
    private static final String dataProgramari = "/Users/diana.petre/Documents/facultate/seminar_6/src/date/programari.txt";

    public static void main(String[] args) throws Exception {
        Map<Integer, Profesor> profesori = new HashMap<>();

        try (var buffer = new BufferedReader(new FileReader(dataProfesori))) {
            profesori = buffer.lines()
                    .map(Main::getProfesor)
                    .collect(Collectors.toMap(profesor -> profesor.getId(), profesor -> profesor));
//                    .forEach(System.out::println);
        }

        profesori.forEach((key, value) -> {
            System.out.println("ID: " + key);
            System.out.println("Value: " + value);
            System.out.println("-----");
        });

        List<Programare> programari = new ArrayList<>();
        try (var buffer = new BufferedReader(new FileReader(dataProgramari))) {
            Map<Integer, Profesor> finalProfesori = profesori;
            programari = buffer.lines()
                    .map(linie -> getProgramare(linie, finalProfesori))
                    .toList();
        }

        programari.forEach(System.out::println);
//        for (int i = 0; i < programari.size(); ++i) {
//            System.out.println(programari.get(i));
//        }
//        for (Programare programare : programari) {
//            System.out.println(programare);
//        }

        System.out.println("---------");
        System.out.println("Afisare cursuri in ordine alfabetica");
        programari.stream()
                .filter(Programare::isEsteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println("---------");
        System.out.println("Afisare numar de activitati pentru fiecare profesor");
        System.out.println("Profesor --- Cursuri --- Laboratoare");
//        HashMap<Profesor, List<Programare>>
        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor))
                .forEach((profesor, programare) -> {
                    String nume = profesor.getPrenume() + " " + profesor.getNume();
                    long numarCursuri = programare
                            .stream()
                            .filter(Programare::isEsteCurs)
                            .count();

                    long numarLabs = programare
                            .stream()
                            .filter(prog -> !prog.isEsteCurs())
                            .count();
                    System.out.println(nume + " " + numarCursuri + " " + numarLabs);
                });
    }

    private static Programare getProgramare(String linie, Map<Integer, Profesor> finalProfesori) {
        String[] date = linie.split("\t");
        String ziua = date[0];
        String interval = date[1];
        Profesor profesor = finalProfesori.get(Integer.parseInt(date[2]));
        String disciplina = date[3];
        String sala = date[4];
        boolean esteCurs = Boolean.parseBoolean(date[5]);
        String serie = date[6];

        return new Programare(ziua, interval, profesor, disciplina, sala, esteCurs, serie);
    }

    private static Profesor getProfesor(String linie) {
        String[] date = linie.split("\t");
        int id = Integer.parseInt(date[0]);
        String prenume = date[1];
        String nume = date[2];
        String departament = date[3];

        return new Profesor(id, prenume, nume, departament);
    }
}