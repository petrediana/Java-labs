package seminar_5;

import java.io.Serializable;
import java.util.Objects;

public class Hobby implements Cloneable, Serializable {
    private final String name;
    private final int likeness;

    public Hobby(String name, int likeness) {
        this.name = name;
        this.likeness = likeness;
    }

    public String getName() {
        return name;
    }

    public int getLikeness() {
        return likeness;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "name='" + name + '\'' +
                ", likeness=" + likeness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hobby hobby = (Hobby) o;
        return likeness == hobby.likeness && Objects.equals(name, hobby.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, likeness);
    }

    @Override
    protected Hobby clone() {
        try {
            Hobby shallowCopy = (Hobby) super.clone();
            return new Hobby(shallowCopy.name, shallowCopy.likeness);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
