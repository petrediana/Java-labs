package seminar_7;

import java.util.ArrayList;
import java.util.List;

class Cont {
    int sold = 0;

    public synchronized void depune(int suma) {
        sold += suma;
    }

    public int getSold() {
        return sold;
    }
}

public class Ex2 {
    public static void main(String[] args) throws InterruptedException {
        var cont = new Cont();

        List<Thread> fire = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var fir = new Thread(() -> {
                for (int index = 0; index < 1000; index++) {
                    cont.depune(1);
                }
            });
            fire.add(fir);
            fir.start();
        }

        for (var fir : fire) {
            fir.join();
        }

        System.out.println("Suma este " + cont.getSold());
    }
}
