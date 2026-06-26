import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LRUCacheV2SynchronizedTest{
    public static void main(String[] args) throws Exception{
        int threadNumber = 4;
        int capacity = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threadNumber);
        LRUCache<Integer, String> cache = new LRUCache<>(capacity);

        long start = System.nanoTime(); // from here tracking time like how much time it's taking 

        for(int j=0;j<threadNumber;j++){
            final int threadId = j;
            pool.execute(()->{
                for(int i=0;i<100;i++){
                        cache.put((threadId * 100 + i), "LRU Cache data");
                }
            });
        }   
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES); // wait for all tasks to finish

        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;

        System.out.println("==== LRU Cache V2 Synchronized Test ====");
        System.out.println("Threads: " + threadNumber);
        System.out.println("Capacity: " + capacity);
        System.out.println("Final size: " + cache.list.size());
        System.out.println("Expected size: " + capacity);
        System.out.println("Correct: " + (cache.list.size() == capacity));
        System.out.println("Time taken: " + durationMs + "ms");

    }
}
