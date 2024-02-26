
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Nota nota1 = new Nota("JAVA", 10);

        System.out.printf("Materia: %s, punctajul: %d", nota1.getMaterie(), nota1.getPunctaj());
        System.out.println("Nota este: " + nota1);

        nota1.setMaterie("SDD");
        nota1.setPunctaj(9);

        try {
            nota1.setPunctaj(-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Dupa modificare campuri: " + nota1);

        System.out.println("-------------");

        Student student1 = new Student(1, "Nume Prenume", "1053 D");
        System.out.println(student1);

        Nota nota2 = new Nota("JAVA", 10);
        Nota[] note = new Nota[] { nota1, nota2 };

        System.out.println(Arrays.toString(note));

        student1.setNote(note);
        System.out.println(student1);

        note[0].setMaterie("Materie cu alt nume!");
        System.out.println(student1);

        System.out.println("-------------");
        Student[] studenti = citireStudenti();
        System.out.println(Arrays.toString(studenti));
    }

    /*
      Denumire camp | input
    * Numar Studenti: 3
    * Numar note studenti: 2
    * Id, Nume Prenume, Grupa | 0,Alex Ion,1053 D
    * Materie, punctaj | SDD, 10
    * Materie, punctaj | JAVA, 10
    * ...
    * */
    private static Student[] citireStudenti() {
        System.out.println("Citire studenti linie comanda");
        Scanner scanner = new Scanner(System.in);
        int numarStudenti = Integer.parseInt(scanner.nextLine().trim());
        int numarNote = Integer.parseInt(scanner.nextLine().trim());

        System.out.printf("Numar studenti: %d, numar note pentru fiecare student: %d\n", numarStudenti, numarNote);

        Student[] studenti = new Student[numarStudenti];

        for (int i = 0; i < numarStudenti; ++i) {
            String linie = scanner.nextLine();
            String[] valori = linie.split(",");

            studenti[i] = new Student(Integer.parseInt(valori[0]), valori[1], valori[2]);

            Nota[] note = new Nota[numarNote];
            for (int j = 0; j < numarNote; ++j) {
                String[] valoriNote = scanner.nextLine().split(",");
                note[j] = new Nota(valoriNote[0], Integer.parseInt(valoriNote[1]));
            }

            studenti[i].setNote(note);
        }

        return studenti;
    }
}
