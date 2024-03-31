package seminar_5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person implements Cloneable, Serializable {
    private final String name;
    private final int age;
    private final List<Hobby> hobbies;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;

        this.hobbies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobbies=" + hobbies +
                '}';
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
    }

    public int getHobbiesSize() {
        return this.hobbies.size();
    }

    public Hobby getHobbyAtIndex(int index) {
        return this.hobbies.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(hobbies, person.hobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, hobbies);
    }

    @Override
    public Person clone() {
        try {
            Person shallowCopy = (Person) super.clone();

            Person deepCopy = new Person(shallowCopy.name, shallowCopy.age);
            for (int i = 0; i < shallowCopy.hobbies.size(); ++i) {
                deepCopy.addHobby(shallowCopy.getHobbyAtIndex(i).clone());
            }

            return deepCopy;
        } catch (CloneNotSupportedException exception) {
            System.out.println(exception.getMessage());
            throw new AssertionError();
        }
    }
}
