/*

Exemplu: Citire obiect oarecare

Fie următoarele adnotări opționale pentru câmpuri:
- IgnoraCamp - campul va fi ignorat la citire
- Mesaj(string) - mesajul care trebuie afișat pentru utilizator la citirea valorii
- Dimensiune(int minim,int maxim) - dimensiunile valide pentru un câmp de tip String

Se cere să se scrie o metodă care primește ca parametru o clasă și citește de
la consolă un obiect respectând adnotările de mai sus aplicate câmpurilor clasei.

Se presupune că pentru fiecare câmp ne-ignorat există o metodă publică set corespunzătoare.

*/

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME)
@interface IgnoraCamp {
}

@Retention(RetentionPolicy.RUNTIME)
@interface Mesaj {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface Dimensiune {
    int minim();

    int maxim();
}

public class Program03_CitireObiect {

    public static Object CitireObiect(Class<?> clasa)
            throws NoSuchMethodException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException {

        var scanner = new Scanner(System.in);

        // 1. Construim o instanță din obiectul solicitat
        // folosind constructorul implicit
        var rezultat = clasa.getConstructor().newInstance();

        // 2. Pentru fiecare câmp declarat în clasă
        for (var camp : clasa.getDeclaredFields()) {
            if (Modifier.isStatic(camp.getModifiers())) {
                continue;   // ignorăm câmpurile statice
            }

            if (camp.isAnnotationPresent(IgnoraCamp.class)) {
                continue;   // ignorăm câmpurile marcate cu @IgnoraCamp
            }

            // Afișăm mesajul specificat sau un mesaj implicit dacă nu există
            String mesaj = String.format(
                    "Introduceți valoarea pentru câmpul '%s': ",
                    camp.getName());
            if (camp.isAnnotationPresent(Mesaj.class)) {
                mesaj = camp.getAnnotationsByType(Mesaj.class)[0].value();
            }

            System.out.print(mesaj);

            // Citim valoarea introdusă de utilizator ca string
            var linie = scanner.nextLine();

            // O convertim la tipul câmpului
            Object valoare = null;
            if (camp.getType() == String.class) {
                valoare = linie;

                // pentru câmpuri de tip string validăm și dimensiunea
                // (dacă este specificată)
                if (camp.isAnnotationPresent(Dimensiune.class)) {
                    var dimensiune = camp.getAnnotationsByType(Dimensiune.class)[0];
                    if (linie.length() < dimensiune.minim()
                    || linie.length() > dimensiune.maxim()) {
                        throw new AssertionError(
                            "Dimensiune invalidă pentru câmpul " + camp.getName());
                    }
                }

            } else if (camp.getType() == int.class) {
                valoare = Integer.parseInt(linie);
            } else {
                throw new UnsupportedOperationException(
                        "Tipul " + camp.getType().getName() + " nu este suportat momentan.");
            }

            // Obținem metoda de set corespunzătoare și o apelăm
            // Pentru un camp 'pret' -> căutăm metoda 'setPret'
            // care are un singur parametru de tipul campului.
            var denumire = "set"
                    + camp.getName().substring(0, 1).toUpperCase()
                    + camp.getName().substring(1);

            var metodaSet = clasa.getMethod(denumire, camp.getType());
            metodaSet.invoke(rezultat, valoare);
        }

        return rezultat;
    }

    static void afisareObiect(Object obiect) throws Exception {
        Class classInfo = obiect.getClass();
        System.out.print(classInfo.getName() + ": ");

        while(classInfo != Object.class) {
            for (Field camp : classInfo.getDeclaredFields()) {
                if (Modifier.isStatic(camp.getModifiers())){
                    continue;
                }
                camp.setAccessible(true);
                System.out.printf("%s (%s)='%s' ",
                        camp.getName(),
                        camp.getType().getName(),
                        camp.get(obiect));
            }
            classInfo = classInfo.getSuperclass();
        }
        System.out.println();
    }

    public static void main(String[] args)
            throws Exception {

        Produs produs = (Produs) CitireObiect(Produs.class);
        afisareObiect(produs);
    }
}

class Produs {

    @Mesaj("Introduceti denumirea produsului: ")
    @Dimensiune(minim = 3, maxim = 10)
    String denumire;

    @IgnoraCamp
    int cantitate;

    int pret;

    public Produs() {
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pret=").append(pret);
        sb.append('}');
        return sb.toString();
    }
}