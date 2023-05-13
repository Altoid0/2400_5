//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//
public class Queue<T> implements QueueInterface<T> {
    private Node firstNode;
    private Node lastNode;
    private int size;
    
    public Queue() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }
    
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);
        if (isEmpty()) {
            firstNode = newNode;
        }
        else {
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
        size++;
    }
    
    public T dequeue() {
        T front = null;
        if (!isEmpty()) {
            front = firstNode.getData();
            firstNode = firstNode.getNextNode();
            size--;
            if (isEmpty()) {
                lastNode = null;
            }
        }
        return front;
    }
    
    public T getFront() {
        T front = null;
        if (!isEmpty()) {
            front = firstNode.getData();
        }
        return front;
    }
    
    public boolean isEmpty() {
        return firstNode == null;
    }
    
    public void clear() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }
    
    public int size() {
        return size;
    }
    
    private class Node {
        private T data;
        private Node next;
        
        private Node(T dataPortion) {
            this(dataPortion, null);
        }
        
        private Node(T dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        }
        
        private T getData() {
            return data;
        }
        
        private void setData(T newData) {
            data = newData;
        }
        
        private Node getNextNode() {
            return next;
        }
        
        private void setNextNode(Node nextNode) {
            next = nextNode;
        }
    }
}
