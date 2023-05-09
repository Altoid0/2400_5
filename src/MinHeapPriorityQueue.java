//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

public class MinHeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T>  {
    private MinHeap<T> minHeap;

    public MinHeapPriorityQueue() {
        minHeap = new MinHeap<>();
    }

    public void add(T newEntry) {
        minHeap.add(newEntry);
    }

    public T remove() {
        return minHeap.removeMin();
    }

    public T peek() {
        return minHeap.getMin();
    }

    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    public int getSize() {
        return minHeap.getSize();
    }

    public void clear() {
        minHeap.clear();
    }

    public void showHeap(int index) {
        minHeap.showHeap(index);
    }
}
