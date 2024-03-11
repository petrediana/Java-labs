import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(Marca.Dacia.getDenumire());
        System.out.println(Marca.Ford);
        System.out.println(Marca.Suzuki);

        Masina spring = new Masina(1, "spring",
                Marca.Dacia, 2020);

        Masina copieSpring = new Masina(
                spring.getId(),
                spring.getModel(),
                spring.getMarca(),
                spring.getAnFabricare()
        );

        spring.setAnFabricare(9999999);
        System.out.println(copieSpring);

        System.out.println(spring.getId());
        System.out.println(spring.getMarca());
        System.out.println(spring.getModel());
        System.out.println(spring.getAnFabricare());

        System.out.println(spring);

        try {
            Masina cazan = new Masina(10, "logan",
                    Marca.Dacia, 1998);
            System.out.println("Eu nu ma execut!!");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Masina focus = new Masina(-1, "focus",
                    Marca.Ford, 1998);
            System.out.println("Eu nu ma execut!!");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        List<Masina> masini = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String linie = scanner.next();
            String[] campuri = linie.split(",");
            try {
                int id = Integer.parseInt(campuri[0]);
                String model = campuri[1];
                Marca marca = getMarcaDupaString(campuri[2]);
                int anFabricare = Integer.parseInt(campuri[3]);

                Masina masina = new Masina(
                        id, model, marca, anFabricare
                );
                masini.add(masina);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println(masini);
        System.out.println(getMarcaDupaString("fORD"));

    }

    private static Marca getMarcaDupaString(String denumire) {
        switch (denumire.toLowerCase()) {
            case "dacia":
                return Marca.Dacia;

            case "ford":
                return Marca.Ford;

            case "suzuki":
                return Marca.Suzuki;

            default:
                return null;
        }
    }
}