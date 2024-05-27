import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Profesor> profesori = new ArrayList<>();
        try (var fisier = new FileReader("date/profesori.json")) {

            var jsonArrayProfesori = new JSONArray(new JSONTokener(fisier));
            profesori = StreamSupport.stream(jsonArrayProfesori.spliterator(), false)
                    .map(obiect -> (JSONObject)obiect)
                    .map(obiect -> new Profesor(
                            obiect.getInt("idProfesor"),
                            obiect.getString("prenume"),
                            obiect.getString("nume"),
                            obiect.getString("departament")
                    ))
                    .collect(Collectors.toList());
        }

        System.out.println(profesori);

        Map<String, List<Profesor>> grupare = new HashMap<>();
        for (int i = 0; i < profesori.size(); ++i) {
            var profesor = profesori.get(i);

            if (grupare.containsKey(profesor.getDepartament())) {
                grupare.get(profesor.getDepartament()).add(profesor);
            } else {
                grupare.put(profesor.getDepartament(), new ArrayList<>());
                grupare.get(profesor.getDepartament()).add(profesor);
            }
        }

        System.out.println("------");

        System.out.println(grupare);

        System.out.println("------");

        System.out.println(grupare.get("CERCETARE"));
        System.out.println(grupare.get("CERCETARE").size());

        System.out.println("------");


        Map<String, List<Profesor>> grupareStream = profesori.stream()
                .collect(Collectors.groupingBy(Profesor::getDepartament));

        List<Departament> departamente = grupareStream
                .entrySet()
                .stream()
                .map(entry -> new Departament(
                        entry.getKey(),
                        entry.getValue().size()
                ))
                .sorted((dep1, dep2) -> Integer.compare(dep2.getNumarProfesori(), dep1.getNumarProfesori()))
                .collect(Collectors.toList());

        System.out.println(departamente);

        System.out.println("------");

        // Salvare in fisier JSON
        JSONArray jsonArrayDepartamente = new JSONArray();
        for (var departament : departamente) {
            JSONObject jsonDepartament = new JSONObject();
            jsonDepartament.put("denumire", departament.getDenumire());
            jsonDepartament.put("numarProfesori", departament.getNumarProfesori());

            jsonArrayDepartamente.put(jsonDepartament);
        }

        try (var fisier = new FileWriter("date/departamente.json")) {
            jsonArrayDepartamente.write(fisier, 3, 0);
        }

    }
}