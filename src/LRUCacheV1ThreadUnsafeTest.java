import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LRUCacheV1ThreadUnsafeTest{
    public static void main(String[] args) throws Exception{
        int threadNumber = 4;
        ExecutorService pool = Executors.newFixedThreadPool(threadNumber);
        LRUCache<Integer, String> cache = new LRUCache<>(10);

        
        for(int j=0;j<threadNumber;j++){
            final int threadId = j;
            pool.execute(()->{
                for(int i=0;i<100;i++){
                        cache.put((threadId * 100 + i), "Hello");
                }
            });
        }   
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES); // wait for all tasks
        System.out.println(cache.list.size());

    }
}