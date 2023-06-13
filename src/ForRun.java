import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class ForRun {
    public static void main(String[] args) {
        int[] arr = new int[10000000];
        for (int i=0;i<10000000;i++){
            arr[i] = (int) (Math.random()*1000000);
        }
        int[] arr2 = arr;
        ParallelQuicksort pq = new ParallelQuicksort();
        ParallelMergeSort pm = new ParallelMergeSort();
        Instant start = Instant.now();
        pq.parallelQuicksort(arr);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for Parallel Quick Sort: "+ Math.abs(timeElapsed.getNano()/1000) +" microseconds");
        start = Instant.now();
        pm.parallelMergeSort(arr2);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for Parallel Merge Sort: "+ Math.abs(timeElapsed.getNano()/1000) +" microseconds");
    }
}
