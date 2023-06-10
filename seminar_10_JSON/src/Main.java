import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        // JSON - JavaScript Object Notation {}

        List<Profesor> profesori = new ArrayList<>();
        try (var fisier = new FileReader("date\\profesori.json")) {

            /* Varianta 1 cu for clasic */
            var jsonArrayProfesori = new JSONArray(new JSONTokener(fisier));
//            for (int i = 0; i < jsonArrayProfesori.length(); ++i) {
//                var jsonProfesor = jsonArrayProfesori.getJSONObject(i);
//
//                profesori.add(
//                        new Profesor(
//                                jsonProfesor.getInt("idProfesor"),
//                                jsonProfesor.getString("prenume"),
//                                jsonProfesor.getString("nume"),
//                                jsonProfesor.getString("departament")
//                        )
//                );
//            }

            /* Varianta 2 cu stream api */
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
        System.out.println(profesori.get(0));

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

//        System.out.println(grupare.get("CERCETARE"));
//        System.out.println(grupare.get("CERCETARE").size());

        Map<String, List<Profesor>> grupareStream = profesori.stream()
                        .collect(Collectors.groupingBy(Profesor::getDepartament));

//        System.out.println(grupareStream.get("CERCETARE"));
//        System.out.println(grupareStream.get("CERCETARE").size());


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

        // Salvare in fisier JSON
        JSONArray jsonArrayDepartamente = new JSONArray();
        for (var departament : departamente) {
            JSONObject jsonDepartament = new JSONObject();
            jsonDepartament.put("denumire", departament.getDenumire());
            jsonDepartament.put("numarProfesori", departament.getNumarProfesori());

            jsonArrayDepartamente.put(jsonDepartament);
        }

        try (var fisier = new FileWriter("date\\departamente.json")) {
            jsonArrayDepartamente.write(fisier, 3, 0);
        }
    }
}