package seminar;

import java.lang.reflect.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        // 1. Obtinem instana unei clase -> Class<Tip>
        Masina masina = new Masina("subaru", 1);

        Class<?> clasaMasina = masina.getClass();
        Class<?> clasaString = Class.forName("java.lang.String");
        Class<?> clasaInt = int.class;

        System.out.println(clasaMasina);
        System.out.println(clasaString);
        System.out.println(clasaInt);

        // 2. Obtinem informatii despre clasa
        System.out.println("Modificatori de acces: " + clasaString.getName());
        var modificatoriString = clasaString.getModifiers();
        System.out.println(Modifier.toString(modificatoriString));

        // 3. Manipulare de campuri
        System.out.println("\n----Manipulare de campuri----");
        for (Field camp: clasaMasina.getDeclaredFields()) {
            camp.setAccessible(true); // Obligatoriu pentru campurile private

            System.out.printf("%s - %s - %s = %s %n", Modifier.toString(camp.getModifiers()),
                    camp.getType().getName(), camp.getName(), camp.get(masina));
        }

        var campCod = clasaMasina.getDeclaredField("cod");
        campCod.setAccessible(true);
        campCod.set(masina, 130);
        System.out.println(masina);

        // 4. Utilizare de metode
        for (Method metoda : clasaMasina.getDeclaredMethods()) {
            var modificatorMetoda = Modifier.toString(metoda.getModifiers());
            var tipReturnatMetoda = metoda.getReturnType();
            var numeMetoda = metoda.getName();

            System.out.printf("%s %s %s are params: ", modificatorMetoda, tipReturnatMetoda.getName(), numeMetoda);

            for (Parameter param: metoda.getParameters()) {
                System.out.printf(param.getType().getName());
            }

            System.out.println("\n");
        }

        // Invocare
        Method metodaSetCod = clasaMasina.getDeclaredMethod("setCod", int.class);
        metodaSetCod.invoke(masina, 10);
        System.out.println(masina);

        // 5. Utilizare constructor
        for (Constructor<?> constructor : clasaMasina.getConstructors()) {
            System.out.println("Constructor cu params: ");
            for (Parameter param: constructor.getParameters()) {
                System.out.println(param.getType().getName() + " " + param.getName());
            }
        }

        var constructor = clasaMasina.getConstructor(String.class, int.class);
        Masina altaMasina = (Masina)constructor.newInstance("Nio", 1);
        System.out.println(altaMasina);
    }
}

class Masina {
    public transient String marca;

    private int cod;

    public Masina(String marca, int cod) {
        this.marca = marca;
        this.cod = cod;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Masina{" +
                "marca='" + marca + '\'' +
                ", cod=" + cod +
                '}';
    }
}