public class MyDoublyLinkedList<K, V>{
    // HEAD and TAIL dummy node are permanent boundary in this Doubly linked list
    private Node<K, V> head, tail;
    private int size = 0;

    public MyDoublyLinkedList(){
        // Node<> this diamond symbol based Node of specific type, this kind of code only allowed with new keyword 
        // we can't use this simple diamond operator without K , V when we are not creating object
        // while object creation using new keyword at that time ONLY AND ONLY we can use diamond operator <>

        this.head = new Node<>();
        this.tail = new Node<>();

        // link them each other
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public int size(){
        return this.size;
    }

    private Node<K, V> createNode(K key, V value, long expirationTime){
        return new Node<>(key, value, expirationTime);
    }

    public void addToHead(Node<K, V> node){
        node.next = this.head.next;
        this.head.next.prev = node;
        node.prev = head;
        this.head.next = node;

        this.size++;

    }

    public Node<K, V> addToHead(K key, V value){
        return addToHead(key, value, 0);
    }

    public Node<K, V> addToHead(K key, V value, long expirationTime){
        Node<K, V> newNode = createNode(key, value, expirationTime);
        newNode.next = head.next;
        newNode.prev = head;
        head.next.prev = newNode;
        head.next = newNode;

        this.size++;
        
        return newNode;
    }

    // make another method for  addToHead as well which can receive a node directly and add it to the head
    public Node<K, V> addToTail(Node<K, V> node){
        return addToTail(node.key, node.value, 0);
    }

    public Node<K, V> addToTail(K key, V value){
        return addToTail(key, value, 0);
    }

    public Node<K, V> addToTail(K key, V value, long expirationTime){
        Node<K, V> newNode = createNode(key, value, expirationTime);
        newNode.next = tail;
        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        tail.prev = newNode;

        this.size++;

        return newNode;
    }

    public void removeNode(Node<K, V> node){
        // This method needs the help of HashMap to find out that particular Node at O(1) then remove.
        // Hashmap only used for LRU cache not here
        if(this.head.next == tail || node == null || node == head || node == tail){
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;

        this.size--;


    }

    public Node<K, V> removeTail(){
        if(this.head.next == this.tail){
            System.out.println("Empty List.");
            return null;
        }

        Node<K, V> deletedNode = tail.prev;
        tail.prev.prev.next = tail;
        tail.prev = tail.prev.prev;

        // Remove rest of the links from deletedNode so GC can clear it.
        deletedNode.prev = null;
        deletedNode.next = null;

        this.size--;

        return deletedNode;
    
    }

    // It consist of two step operation remove the Node and then add that Node to the head.
    public void moveToHead(Node<K, V> node){
        if(this.head.next == this.tail || node == null || node == head || node == tail){
            return;
        }

        removeNode(node);
        addToHead(node);

     
    }

    // Check if cache is Empty or not?
    public boolean isEmpty(){
        if(this.head.next == this.tail){
            return true;
        }else{
            return false;
        }
    }
    
    // Method to clear the LRU cached doubly linked list.
    public void clear(){
        this.head.next = tail;
        this.tail.prev = head;
        this.size = 0;
        return;
    }

    // Method to check either a Node has expired or not?
    public boolean isExpired(Node<K, V> node){
        long now = System.currentTimeMillis();
        if(node.expiresAt == 0){
            return false;
        }
        else if(node.expiresAt >= now){
            return false;
        }else{
            return true;
        }
    }

    public void printList(){
        if(head.next == tail){
            System.out.println("Empty list.");
            return;
        }
        
        Node<K, V> curr = this.head.next;
        while(curr.next != null){
            if(curr.next != tail){
                System.out.print(curr.value+" <=> ");
            }
            else{
                System.out.print(curr.value);
            }

            curr = curr.next;
        }
        System.out.println();
    }

    

}