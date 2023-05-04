package seminar_7;

import java.util.ArrayList;
import java.util.List;

class FirNumarare extends Thread {
    private int n = 0;
    static volatile long idCastigator = 0;

    @Override
    public void run() {

        for (int n = 1; n <= 100; n++) {
            try {
                Thread.sleep(1 + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
            }
            if (idCastigator > 0) {
                System.out.println(getId() + " - am pierdut - am ajuns până la " + n);
                return;
            }
            System.out.printf("#%2d: %d%n", getId(), n);
        }
        idCastigator = getId();
    }
}

public class Ex1 {
    public static void main(String[] args) throws InterruptedException {

        // 1. Pornim firele de execuție și reținem referințele într-o listă
        List<Thread> fire = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var fir = new FirNumarare();
            fire.add(fir);
            fir.start();
        }

        // 2. Așteptăm până când toate firele de execuție s-au terminat
        while (fire.stream().anyMatch(fir -> fir.isAlive())) {
        }

        // 3. Afișăm câștigătorul
        System.out.println("Câștigătorul este " + FirNumarare.idCastigator);
    }
}

