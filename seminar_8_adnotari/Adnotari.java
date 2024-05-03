package seminar;


import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface ExempluAdnotareSimpla {

}
@Retention(RetentionPolicy.RUNTIME)
@interface AltExempluAdnotareSimpla {

}

@interface AdnotareSingleElement {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface AdnotareValori {
    String valoare1();
    int valoare2();
}

@ExempluAdnotareSimpla
@AdnotareSingleElement(value="Hello")
@AdnotareValori(valoare1 = "World!", valoare2 = 420)
class Dummy {
    @ExempluAdnotareSimpla
    int numar;
}


public class Adnotari {
    public static void main(String[] args) {
        var clasaDummy = Dummy.class;

        // Putem verifica prezenta adnotarilor
        if (clasaDummy.isAnnotationPresent(ExempluAdnotareSimpla.class)) {
            System.out.println("Clasa Dummy este decorata cu ExempluAdnotareSimpla");
        }

        if (clasaDummy.isAnnotationPresent(AltExempluAdnotareSimpla.class)) {
            System.out.println("Clasa Dummy este decorata cu: AltExempluAdnotareSimpla");
        }

        // Enumerare adnotari
        for (Annotation adnotare : clasaDummy.getAnnotations()) {
            System.out.println(adnotare);
        }

        // Pot obtine valori
        AdnotareValori adnotareValori = clasaDummy.getAnnotation(AdnotareValori.class);
        System.out.println(adnotareValori.valoare1() + " " + adnotareValori.valoare2());
    }
}