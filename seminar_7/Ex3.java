package seminar_7;

public class Ex3 {
    static class FirMaxim extends Thread {

        private final int[] vector;
        private final int indexStart, indexEnd; // inclusiv
        private int rezultat;

        public FirMaxim(int[] vector, int indexStart, int indexEnd) {
            this.vector = vector;
            this.indexStart = indexStart;
            this.indexEnd = indexEnd;
        }

        @Override
        public void run() {
            rezultat = vector[indexStart];
            for (int i = indexStart + 1; i <= indexEnd; i++) {
                rezultat = Math.max(rezultat, vector[i]);
            }
        }

        public int getRezultat() {
            return rezultat;
        }
    }

    public static int getMaxim(int[] vector) {
        if (vector.length == 0) {
            return 0;
        } else if (vector.length == 1) {
            return vector[0];
        }

        var fir1 = new FirMaxim(vector, 0, vector.length / 2 - 1);
        var fir2 = new FirMaxim(vector, vector.length / 2, vector.length - 1);

        fir1.start();
        fir2.start();

        try {
            fir1.join();
            fir2.join();
        } catch (InterruptedException e) {

        }

        return Math.max(fir1.getRezultat(), fir2.getRezultat());
    }

    public static void main(String[] args) {
        var vector = new int[]{1, 2, 3, 5, 2, 1, 4, 6, 7, 110, 3};
        System.out.println("Valoarea maxima este " + getMaxim(vector));
    }
}
