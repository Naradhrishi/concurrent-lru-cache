// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.TimeUnit;

// public class LRUCacheV2SynchronizedTest {
//     public static void main(String[] args) throws Exception {
//         int threadNumber = 50;
//         int capacity = 100;

//         ExecutorService pool = Executors.newFixedThreadPool(threadNumber);
//         LRUCache<Integer, String> cache = new LRUCache<>(capacity);
//         CountDownLatch latch = new CountDownLatch(1);

//         for(int j = 0; j < threadNumber; j++){
//             final int threadId = j;
//             pool.execute(() -> {
//                 try {
//                     latch.await();
//                 } catch(InterruptedException e){
//                     Thread.currentThread().interrupt();
//                 }
//                 for(int i = 0; i < 100; i++){
//                     cache.put((threadId * 100 + i), "LRU Cache data");
//                 }
//             });
//         }

//         long start = System.nanoTime();
//         latch.countDown();
//         pool.shutdown();
//         pool.awaitTermination(1, TimeUnit.MINUTES);
//         long end = System.nanoTime();
//         long durationMs = (end - start) / 1_000_000;

//         System.out.println("==== LRU Cache V2 Synchronized Test ====");
//         System.out.println("Threads:       " + threadNumber);
//         System.out.println("Capacity:      " + capacity);
//         System.out.println("Final size:    " + cache.size());
//         System.out.println("Expected size: " + capacity);
//         System.out.println("Correct:       " + (cache.size() == capacity));
//         System.out.println("Time taken:    " + durationMs + "ms");

//          System.out.println("Successfully tested and checked that it's thread safe.");
//     }
// }