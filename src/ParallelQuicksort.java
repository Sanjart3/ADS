import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuicksort {

    public static void parallelQuicksort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new QuicksortTask(array, 0, array.length - 1));
        forkJoinPool.shutdown();
    }

    private static class QuicksortTask extends RecursiveAction {
        private int[] array;
        private int start;
        private int end;

        public QuicksortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (start < end) {
                int pivotIndex = partition(array, start, end);
                QuicksortTask leftTask = new QuicksortTask(array, start, pivotIndex - 1);
                QuicksortTask rightTask = new QuicksortTask(array, pivotIndex + 1, end);
                invokeAll(leftTask, rightTask);
            }
        }

        private int partition(int[] array, int start, int end) {
            int pivot = array[end];
            int i = start - 1;

            for (int j = start; j < end; j++) {
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                }
            }

            swap(array, i + 1, end);
            return i + 1;
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
