//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T> {
    private Node topNode; // References the first node on the stack

    public LinkedStack() {
        topNode = null;
    }

    public void push(T newEntry) {
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T top = peek();
        assert topNode != null; // If false throws an AssertionError
        topNode = topNode.getNextNode();
        return top;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        else {
            T top = null;
            if (topNode != null) {
                top = topNode.getData();
            }
            return top;
        }
    }

    public boolean isEmpty() {
        return topNode == null;
    }

    public void clear() {
        //topNode = null; // Not the cleanest way to clear the stack
        while (!isEmpty()) {
            pop();
        }
    }

    private class Node {
        private T data; 
        private Node next; // link to next node

        private Node(T dataPortion) {
            this(dataPortion, null);
        }

        private Node(T dataPortion, Node linkPortion) {
            data = dataPortion;
            next = linkPortion;
        }

        private T getData() {
            return data;
        }

        private Node getNextNode() {
            return next;
        }
    }
}
