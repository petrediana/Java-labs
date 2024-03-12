package seminar_3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Carte carte1 = new Carte("Morometii", TipPoveste.Drama, 1980);
        Carte carte2 = new Carte("Harap alb", TipPoveste.Basm, 1970);
        Carte carte3 = new Carte("Ion", TipPoveste.Actiune, 1965);
        Carte carte4 = new Carte("System Design Interview", TipPoveste.Actiune, 2019);

        List<Carte> carti = Arrays.asList(carte1, carte2, carte3, carte4);
        System.out.println(carti);

//        sorteazaListaCarti(carti);
//        System.out.println(carti);

//        carti.sort(new Comparator<Carte>() {
//            @Override
//            public int compare(Carte o1, Carte o2) {
//                return Integer.compare(o2.getAnPublicare(), o1.getAnPublicare());
//            }
//        });

        Carte[] carti2 = new Carte[] { carte1,  carte2, carte3, carte4 };
        Arrays.sort(carti2);
        System.out.println(Arrays.toString(carti2));

        if (carte1.equals(carte2)) {
            System.out.println("Cart1 este egal cu carte2!");
        } else {
            System.out.println("Carte1 NU  ESTE EGAL cu carte2!");
        }

        Carte carte5 = new Carte("Morometii", TipPoveste.Drama, 1980);
        if (carte1.equals(carte5)) {
            System.out.println("Cart1 este egal cu carte5!");
        } else {
            System.out.println("Carte1 NU  ESTE EGAL cu carte5!");
        }

        HashMap<Carte, List<String>> mapa = new HashMap<>();
        mapa.put(carte1, List.of("Cititor1", "Cititor2", "Cititor3", "Cititor4"));

        Carte copieDePeCarte1 = new Carte("Morometii", TipPoveste.Drama, 1980);

        System.out.println(mapa);


        List<String> cititori = mapa.get(copieDePeCarte1);
        if (cititori == null) {
            System.out.println("Nu exista cititori pentru acest obiect!: " + carte1);
        } else {
            System.out.println(cititori);
        }

        Carte copy = carte1.clone();
        System.out.println(carte1);
        System.out.println(copy);

    }

    public static void sorteazaListaCarti(List<Carte> carti) {
        for (int i = 0; i < carti.size() - 1; ++i) {
            for (int j = i + 1; j < carti.size(); ++j) {
                if (carti.get(i).getAnPublicare() > carti.get(j).getAnPublicare()) {
                    Collections.swap(carti, i, j);
                }
            }
        }
    }
}
