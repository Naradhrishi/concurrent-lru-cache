public class LRUCacheStriped<K, V> {
    private final int numberOfSegments = 16;
    private final LRUCache<K, V>[] segments;

    public LRUCacheStriped(int totalCapacity){

        int segmentCapacity = (int) Math.ceil((double) totalCapacity / numberOfSegments);
        this.segments = new LRUCache[numberOfSegments];

        // now create all LRUCache object for all separate segments to have their own Doubly Linked List and hashmap
        for(int i=0; i<numberOfSegments; i++){
            segments[i] = new LRUCache<>(segmentCapacity); // each segment has a particular capacity

        }


    }

    // Function to find out in which segment data has been saved or to save data
    public int getSegmentIndex(K key){
        return Math.abs(key.hashCode()) % numberOfSegments;
    }

    public void put(K key, V value){
        // Before putting we have to find out in which segment it would go.
        int segmentIndex = getSegmentIndex(key);
        segments[segmentIndex].put(key, value);

    }

    public V get(K key){
        // Same here as well like put method, first we have to determine the segment index
        int segmentIndex = getSegmentIndex(key);
        return segments[segmentIndex].get(key);

    }

    public int size(){
        int size = 0;
        for(int i=0;i<numberOfSegments; i++){
            size += segments[i].size();
        }
        return size;
    }

    public int actualCapacity(){
        return this.size();
    }

}
