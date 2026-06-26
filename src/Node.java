public class Node<K, V>{
    K key;
    V value;
    // Expiration would be Node based and it can be different for different Nodes in the list
    long expiresAt;
    Node<K, V> prev, next;

    // Node class constructor without parameter
    public Node(){
        this.key = null;
        this.value = null;
        this.expiresAt = 0;
        this.prev = this.next = null;
    }
    // Node class constructor with key, value parameter
    public Node(K key, V value){
        this.key = key;
        this.value = value;
        this.expiresAt = 0;
        this.prev = this.next = null;
    }

    // Another constructor to set the expiration time
    public Node(K key, V value, long expiresAfter){
        this.key = key;
        this.value = value;
        this.expiresAt = System.currentTimeMillis() + expiresAfter;
        this.prev = this.next = null;
    }
}