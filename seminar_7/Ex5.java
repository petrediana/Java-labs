package seminar_7;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Ex5 {
    private static final int CAPACITATE = 5;
    private static final BlockingQueue<Integer> coada = new ArrayBlockingQueue<>(CAPACITATE);

    private static void producator() {
        while(true) {
            var numar = (int)(Math.random() * 3500);
            try {
                coada.put(numar);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Producatorul este intrerupt!");
                break;
            }
        }
    }

    private static void consumator() {
        while(true) {
            try {
                var numar = coada.take();
                System.out.println("Am consumat numarul: " + numar + " in coada mai sunt: " + coada);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Consumatorul este intrerupt!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        var prod1 = new Thread(Ex5::producator);
        var prod2 = new Thread(Ex5::producator);

        var con1 = new Thread(Ex5::consumator);

        prod1.start();
        prod2.start();

        con1.start();

        new Scanner(System.in).nextLine();

        prod1.interrupt();
        prod2.interrupt();
        con1.interrupt();
    }
}
