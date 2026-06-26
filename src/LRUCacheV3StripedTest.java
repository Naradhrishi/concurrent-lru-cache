import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LRUCacheV3StripedTest {
    public static void main(String[] args) throws Exception {
        int threadNumber = 50;
        int requestedCapacity = 100;

        ExecutorService pool = Executors.newFixedThreadPool(threadNumber);
        LRUCacheStriped<Integer, String> cache = new LRUCacheStriped<>(requestedCapacity);
        CountDownLatch latch = new CountDownLatch(1);

        for(int j = 0; j < threadNumber; j++){
            final int threadId = j;
            pool.execute(() -> {
                try {
                    latch.await(); // all threads wait here until fired
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                for(int i = 0; i < 100; i++){
                    cache.put((threadId * 100 + i), "LRU Cache data");
                }
            });
        }

        long start = System.nanoTime();
        latch.countDown(); // fire all threads simultaneously
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;

        System.out.println("==== LRU Cache V3 Striped Test ====");
        System.out.println("!!!===== Requested size/capacity will only be equal to Actual size/capacity when requested size will be completely divisible by 16. =====!!!");
        System.out.println("Threads:       " + threadNumber);
        System.out.println("Requested size:      " + requestedCapacity);
        System.out.println("Actual size: " + cache.actualCapacity());
        System.out.println("Correct:       " + (cache.size() == cache.actualCapacity()));
        System.out.println("Time taken:    " + durationMs + "ms");
    }
}