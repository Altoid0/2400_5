//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//
import java.util.Iterator;
import java.lang.UnsupportedOperationException;

public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T>{
    private Node firstNode;
    private int size;
    
    public LinkedListWithIterator() {
        firstNode = null;
        size = 0;
    }
    
    public void add(T newEntry) {
        Node newNode = new Node(newEntry);
        if (isEmpty()) {
            firstNode = newNode;
        }
        else {
            Node lastNode = getNodeAt(size);
            lastNode.setNextNode(newNode);
        }
        size++;
    }
    
    public void add(int newPosition, T newEntry) {
        if ((newPosition >= 1) && (newPosition <= size + 1)) {
            Node newNode = new Node(newEntry);
            if (newPosition == 1) {
                newNode.setNextNode(firstNode);
                firstNode = newNode;
            }
            else {
                Node nodeBefore = getNodeAt(newPosition - 1);
                Node nodeAfter = nodeBefore.getNextNode();
                newNode.setNextNode(nodeAfter);
                nodeBefore.setNextNode(newNode);
            }
            size++;
        }
        else {
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
        }
    }
    
    public T remove(int givenPosition) {
        T result = null;
        if ((givenPosition >= 1) && (givenPosition <= size)) {
            if (givenPosition == 1) {
                result = firstNode.getData();
                firstNode = firstNode.getNextNode();
            }
            else {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                Node nodeToRemove = nodeBefore.getNextNode();
                Node nodeAfter = nodeToRemove.getNextNode();
                nodeBefore.setNextNode(nodeAfter);
                result = nodeToRemove.getData();
            }
            size--;
        }
        else {
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
        }
        return result;
    }
    
    public void clear() {
        firstNode = null;
        size = 0;
    }
    
    public T replace(int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= size)) {
            Node desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.getData();
            desiredNode.setData(newEntry);
            return originalEntry;
        }
        else {
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
        }
    }
    
    public T getEntry(int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= size)) {
            return getNodeAt(givenPosition).getData();
        }
        else {
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
        }
    }

    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.getData())) {
                found = true;
            }
            else {
                currentNode = currentNode.getNextNode();
            }
        }
        return found;
    }

    public int getLength() {
        return size;
    }

    private Node getNodeAt(int givenPosition) {
        Node currentNode = firstNode;
        for (int counter = 1; counter < givenPosition; counter++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator<T> getIterator() {
        return new IteratorForLinkedList();
    }

    private class IteratorForLinkedList implements Iterator<T> {
        private Node nextNode;
        
        private IteratorForLinkedList() {
            nextNode = firstNode;
        }
        
        public boolean hasNext() {
            return nextNode != null;
        }
        
        public T next() {
            if (hasNext()) {
                Node returnNode = nextNode;
                nextNode = nextNode.getNextNode();
                return returnNode.getData();
            }
            else {
                throw new java.util.NoSuchElementException("Illegal call to next(); iterator is after end of list.");
            }
        }
        
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by this iterator.");
        }
    }


    private class Node {
        private T data;
        private Node next;
        
        private Node(T dataPortion) {
            data = dataPortion;
            next = null;
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
