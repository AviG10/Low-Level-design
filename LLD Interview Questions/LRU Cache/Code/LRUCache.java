package Code;

import java.util.HashMap;
import java.util.Map;

class LRUCache<K,V> {
    private final int capacity;
    private final DoublyLinkedList<K, V> list;
    private final Map<K, Node<K, V>> map;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    public synchronized V get(K key){
        if(map.containsKey(key)){ // present in map
            Node<K, V> node = map.get(key);
            list.moveToFront(node);
            return node.value;
        }

        return null; // Not present in map
    }

    public synchronized void put(K key, V value){
        if(map.containsKey(key)){ // present in map
            Node<K, V> node = map.get(key);
            node.value = value;
            list.moveToFront(node);
        }
        else { //Not present in map
            if(map.size() == capacity){ // Cache size is reached
                Node<K, V> lruNode = list.removeLast();

                if(lruNode != null) {
                    map.remove(lruNode.key);
                }

            }

            // For Both Cache size reached and not reached
            Node<K,V> newNode = new Node<>(key, value);
            map.put(key, newNode);
            list.addFirst(newNode);
        }
    }
}
