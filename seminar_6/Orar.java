import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Profesor {
    private final int idProfesor;
    private final String prenume;
    private final String nume;
    private final String departament;

    public Profesor(int idProfesor, String prenume, String nume, String departament) {
        this.idProfesor = idProfesor;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getNumeComplet() {
        return nume + ' ' + prenume;
    }
    public String getDepartament() {
        return departament;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profesor{");
        sb.append("idProfesor=").append(idProfesor);
        sb.append(", prenume='").append(prenume).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", departament='").append(departament).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class Programare {
    private final String ziua;
    private final String interval;
    private final Profesor profesor;
    private final String disciplina;
    private final String sala;
    private final boolean esteCurs;
    private final String formatie;

    public Programare(String ziua, String interval, Profesor profesor, String disciplina, String sala, boolean esteCurs, String formatie) {
        this.ziua = ziua;
        this.interval = interval;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.esteCurs = esteCurs;
        this.formatie = formatie;
    }

    public String getZiua() {
        return ziua;
    }

    public String getInterval() {
        return interval;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getSala() {
        return sala;
    }

    public boolean esteCurs() {
        return esteCurs;
    }

    public String getFormatie() {
        return formatie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Programare{");
        sb.append("ziua='").append(ziua).append('\'');
        sb.append(", interval='").append(interval).append('\'');
        sb.append(", profesor=").append(profesor);
        sb.append(", disciplina='").append(disciplina).append('\'');
        sb.append(", sala='").append(sala).append('\'');
        sb.append(", esteCurs=").append(esteCurs);
        sb.append(", formatie='").append(formatie).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

public class Orar {

    public static void main(String[] args) throws Exception {

        // 1. Citire date
        Map<Integer, Profesor> profesori;
        List<Programare> programari;

        try (var fisier = new BufferedReader(new FileReader("data\\profesori.txt"))) {
            profesori = fisier.lines()
                    .map(linie -> new Profesor(
                            Integer.parseInt(linie.split("\t")[0]),
                            linie.split("\t")[1],
                            linie.split("\t")[2],
                            linie.split("\t")[3])
                    )
                    .collect(Collectors.toMap(prof -> prof.getIdProfesor(), prof -> prof));
        }

        try (var fisier = new BufferedReader(new FileReader("data\\programari.txt"))) {
            programari = fisier.lines()
                    .map(linie -> new Programare(
                            linie.split("\t")[0],
                            linie.split("\t")[1],
                            profesori.get(Integer.parseInt(linie.split("\t")[2])),
                            linie.split("\t")[3],
                            linie.split("\t")[4],
                            Boolean.parseBoolean(linie.split("\t")[5]),
                            linie.split("\t")[6])
                    )
                    .collect(Collectors.toList());
        }

        // 2. Prelucrari

        // Afișare lista cursuri în ordine alfabetică
        programari.stream()
                .filter(Programare::esteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(disciplina -> System.out.println(disciplina));

        // Afișare număr de activități pentru fiecare profesor
        System.out.printf("%30s %2s %2s%n", "Profesor", "C", "S");
        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor))
                .forEach((profesor, programariProfesor) -> {
                    System.out.printf("%30s %2d %2d%n",
                            profesor.getNumeComplet(),
                            programariProfesor.stream().filter(Programare::esteCurs).count(),
                            programariProfesor.stream().filter(p -> !p.esteCurs()).count());
                });


        // Lista departamentelor ordonate descrescator dupa numărul de activități
        class Departament {
            String denumire;
            long numarActivitati;

            public Departament(String denumire, long numarActivitati) {
                this.denumire = denumire;
                this.numarActivitati = numarActivitati;
            }

            @Override
            public String toString() {
                return String.format("%-75s - %d activități", denumire, numarActivitati);
            }
        }

        programari.stream()
                .map(programare -> programare.getProfesor().getDepartament())
                .distinct()
                .map(denumire -> {
                    var numarActivitati = programari.stream()
                            .filter(programare -> programare.getProfesor().getDepartament().equals(denumire))
                            .count();
                    return new Departament(denumire, numarActivitati);
                })
                .sorted((a, b) -> Long.compare(b.numarActivitati, a.numarActivitati))
                .forEach(departament -> System.out.println(departament));
    }

}
