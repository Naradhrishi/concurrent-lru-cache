import java.util.*;

public class LRUCache<K, V>{
    private int capacity = 0;
    Map<K, Node<K, V>> map;
    MyDoublyLinkedList<K, V> list;

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

    public synchronized V get(K key){
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
    }
    

    public synchronized void put(K key, V value){
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
    }

}