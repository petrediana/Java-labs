package seminar_1;

public class Main {
    public static void main(String[] args) {
        Nota nota1 = new Nota(10, "Java");
        Nota nota2 = new Nota(10, "SDD");
        Nota nota3 = new Nota(10, "PAW");
        Nota nota4 = new Nota(10, "PEAG");

        Nota[] note1 = new Nota[] { nota1, nota2, nota3 };
        Nota[] note2 = new Nota[] { nota1, nota2, nota3, nota4 };

        Student student = new Student(20, "Nume student", note1);
        note1 = note2;

        System.out.println(student);
        student.afisareNote();
    }
}
