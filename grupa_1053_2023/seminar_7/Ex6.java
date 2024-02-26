package seminar_7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Ex6 {

    static class MaxTask extends RecursiveTask<Integer> {
        private final int[] vector;
        private final int indexStart;
        private final int indexEnd;

        public MaxTask(int[] vector, int indexStart, int indexEnd) {
            this.vector = vector;
            this.indexStart = indexStart;
            this.indexEnd = indexEnd;
        }

        @Override
        protected Integer compute() {
            if (indexEnd == indexStart) {
                return vector[indexStart];
            }

            var indexMiddle = (indexStart + indexEnd) / 2;
            var subTask1 = new MaxTask(vector, indexStart, indexMiddle);
            var subTask2 = new MaxTask(vector, indexMiddle + 1, indexEnd);
            invokeAll(subTask1, subTask2);

            return Math.max(subTask1.join(), subTask2.join());
        }
    }
    public static void main(String[] args) {
        final int[] vector = new int[] { 1, 2, 4134, 321, 31243, 0, -100, 10, 700000, 420};

        var pool = new ForkJoinPool(4);
        var taskMax = new MaxTask(vector, 0, vector.length - 1);

        pool.invoke(taskMax);

        System.out.println("Numarul maxim este: " + taskMax.join());
    }
}
