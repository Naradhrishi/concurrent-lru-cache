import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache<K, V>{
    private int capacity = 0;
    private final Map<K, Node<K, V>> map;
    private final MyDoublyLinkedList<K, V> list;

    private final ReentrantLock lock = new ReentrantLock(true); // saying it to be fair to every Thread

    public LRUCache(){
        this.capacity = 5;
        this.map = new HashMap<>();
        this.list = new MyDoublyLinkedList<>();

    }
    public LRUCache(int cap){
        this.capacity = cap;
        this.map = new HashMap<>();
        this.list = new MyDoublyLinkedList<>();

    }

    public V get(K key){
        lock.lock();
        try{
            if(key == null){
                return null;
            }
            Node<K, V> node = this.map.get(key);
            if(node == null) return null;
    
            if(this.list.isExpired(node)){
                this.list.removeNode(node);
                this.map.remove(key);
                return null;
            }
            this.list.moveToHead(node);
    
            return node.value;

        }finally{
            lock.unlock();
        }
    }
    

    public void put(K key, V value){
        lock.lock();
        try{

            if(key == null || value == null){
                return;
            }
    
            if(map.containsKey(key)){
                Node<K, V> node = this.map.get(key);
                node.value = value;
                this.list.moveToHead(node);
                return;
    
            }
    
            // check if capacity is filled then remove tailNode and then add a new Node to the head.
            if(list.size() >= this.capacity){
                // If the capacity of cache storage has been exhausted then remove the tailNode from doubly linked list and
                //  then remove that nodes key from the hashmap as well so that it doesn't refer to any node which does not exist and got deleted.
                Node<K, V>  tail = this.list.removeTail();
                if(tail != null){
                    this.map.remove(tail.key);
                }
            }
    
            // If capacity was exhausted then remove the tailNode or directly add node to the head of the list.
            Node<K, V> node = new Node<>(key, value);
            this.list.addToHead(node);
            this.map.put(key, node);
    
            return;

        }finally{
            lock.unlock();
        }
    }

    public int size(){
        return this.list.size();
    }

}