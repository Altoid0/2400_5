//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

public final class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> {
    private T[] heap;
    private int lastIndex;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public MinHeap(int initialcapacity) {
        if (initialcapacity < DEFAULT_CAPACITY) {
            initialcapacity = DEFAULT_CAPACITY;
        } else if (initialcapacity > MAX_CAPACITY){
            throw new IllegalStateException("Attempt to create a heap " +
                    "whose capacity exceeds allowed maximum.");
        }

        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[]) new Comparable[initialcapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
    }

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(T[] entries) {
        this(entries.length);
        lastIndex = entries.length;
        for (int index = 0; index < entries.length; index++) {
            heap[index + 1] = entries[index];
        }
        for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) {
            reheap(rootIndex);
        }
    }

    public T getMin() {
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
        }
        return root;
    }

    public boolean isEmpty() {
        return lastIndex < 1;
    }

    public int getSize() {
        return lastIndex;
    }

    public void add(T newEntry) {
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }
        heap[newIndex] = newEntry;
        lastIndex++;
    }

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        while (!done && (leftChildIndex <= lastIndex)) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= lastIndex) &&
                    heap[rightChildIndex].compareTo(heap[largerChildIndex]) < 0) {
                largerChildIndex = rightChildIndex;
            }
            if (orphan.compareTo(heap[largerChildIndex]) > 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    private static <T extends Comparable<? super T>> void reheap(T[] heap, int rootIndex, int lastIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex + 1; // TODO: why is this one +1 and the other one not?
        while (!done && (leftChildIndex <= lastIndex)) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= lastIndex) &&
                    heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }
            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex + 1;
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    public T removeMin() {
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }
        return root;
    }

    public void clear() {
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }

    public void showHeap(int rootIndex) { // TODO: Show the queue, heapsort with new array sort it with Collections.sort and return it all the way to Directed Graph, which casts to EntryPQ
        int left = 2 * rootIndex;
        int right = 2 * rootIndex + 1;

        if (heap[rootIndex] != null) {
            showHeap(left);
            showHeap(right);
            if (heap[left] != null) {
                if (heap[right] != null) {
                    // Print root with left and right child
                    System.out.println(heap[left] + " : " + heap[rootIndex] + " : " + heap[right]);
                }
                else {
                    // Print root with left child
                    System.out.println(heap[left] + " : " + heap[rootIndex] + " : " + ".");
                }
            }
            else {
                // Print leaf
                System.out.println(heap[rootIndex]);
            }
        }
    }
}
