package eu.diana.src;

import java.util.Arrays;

public class Student {
    private int id;
    private String nume;
    private String grupa;
    private Nota[] note;

    public Student(int id, String nume, String grupa) {
        this.id = id;
        this.nume = nume;
        this.grupa = grupa;

        this.note = new Nota[0];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Nota[] getNote() {
        return note;
    }

    public void setNote(Nota[] note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", grupa='" + grupa + '\'' +
                ", note=" + Arrays.toString(note) +
                '}';
    }
}
