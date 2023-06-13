import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {

    private static class MergeSortTask extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;

        public MergeSortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (start < end) {
                int mid = (start + end) / 2;
                MergeSortTask leftTask = new MergeSortTask(array, start, mid);
                MergeSortTask rightTask = new MergeSortTask(array, mid + 1, end);

                invokeAll(leftTask, rightTask);

                merge(array, start, mid, end);
            }
        }

        private void merge(int[] array, int start, int mid, int end) {
            int[] leftArray = Arrays.copyOfRange(array, start, mid + 1);
            int[] rightArray = Arrays.copyOfRange(array, mid + 1, end + 1);

            int i = 0, j = 0, k = start;

            while (i < leftArray.length && j < rightArray.length) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k++] = leftArray[i++];
                } else {
                    array[k++] = rightArray[j++];
                }
            }

            while (i < leftArray.length) {
                array[k++] = leftArray[i++];
            }

            while (j < rightArray.length) {
                array[k++] = rightArray[j++];
            }
        }
    }

    public static void parallelMergeSort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MergeSortTask mergeSortTask = new MergeSortTask(array, 0, array.length - 1);
        forkJoinPool.invoke(mergeSortTask);
    }

    public static void main(String[] args) {
        int[] array = {9, 2, 7, 4, 5, 1, 8, 3, 6};
        System.out.println("Original array: " + Arrays.toString(array));

        parallelMergeSort(array);

        System.out.println("Sorted array: " + Arrays.toString(array));
    }
}