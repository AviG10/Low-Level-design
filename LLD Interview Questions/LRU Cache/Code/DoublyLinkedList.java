package Code;

public class DoublyLinkedList<K, V> {
    private final Node<K, V> head;
    private final Node<K, V> tail;

    public DoublyLinkedList() {
        head = new Node<>(null, null); // dummy head node
        tail = new Node<>(null, null); // dummy tail node

        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(Node<K,V> node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void remove(Node<K, V> node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public Node<K, V> removeLast(){
        if(tail.prev == head){ // If Linked List is empty, i.e. only have dummy nodes head and tail
            return null;
        }

        Node<K, V> last = tail.prev;
        remove(last);

        return last;
    }

    public void moveToFront(Node<K, V> node){
        remove(node);
        addFirst(node);
    }
}