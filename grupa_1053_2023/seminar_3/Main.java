import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(TipTranzactie.IESIRE);
        System.out.println(TipTranzactie.INTRARE);

        System.out.println(TipTranzactie.INTRARE.getSemn());
        System.out.println(TipTranzactie.IESIRE.getSemn());

        Produs p1 = new Produs(10, "Chitara");
        System.out.println(p1);

        Produs p2 = new Produs(4, "Tobe");

        if (p1.equals(p2)) {
            System.out.println("Produsele sunt egale!");
        } else {
            System.out.println("Produsele NU sunt egale!");
        }

        Produs p3 = new Produs(1, "Pian");
        Produs[] produse = new Produs[] {p1, p2, p3};

        System.out.println(Arrays.toString(produse));

        Arrays.sort(produse, new Comparator<Produs>() {
            @Override
            public int compare(Produs produs, Produs t1) {
                return Integer.compare(produs.getCod(), t1.getCod());
            }
        });

        System.out.println(Arrays.toString(produse));

        HashMap<String, Produs> map1 = new HashMap<>();
        map1.put("chitara", p1);
        map1.put("tobe", p2);

        System.out.println(map1);

        HashMap<Produs, List<Tranzactie>> map = new HashMap<>();
        map.put(p1, new ArrayList<>());

        if (map.containsKey(p1)) {
            List<Tranzactie> lista = map.get(p1);
            lista.add(new Tranzactie(p1.getCod(), TipTranzactie.INTRARE, LocalDate.now()));
            System.out.println(map.get(p1));
        }

        if (map.containsKey(p2)) {
            System.out.println("P2 exista");
        } else {
            System.out.println("P2 NU exista");
        }

        AdaugaInHashMap(map,p2);
        AdaugaInHashMap(map, p3);

        System.out.println(map);

        for (Map.Entry<Produs, List<Tranzactie>> entry : map.entrySet()) {
            System.out.println("Cheia cu produsul: " + entry.getKey().getCod());
            System.out.println("Are urmatoarele tranzactii: " + entry.getValue());
        }
    }

    private static void AdaugaInHashMap(HashMap<Produs, List<Tranzactie>> map, Produs produs) {
        if (map.containsKey(produs)) {
            System.out.println("Cheia exista deja!");
        } else {
            map.put(produs, new ArrayList<>());
        }
    }
}

