//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

/**
 * An interface for the ADT minheap.
 */
public interface MinHeapInterface<T extends Comparable<? super T>>
{
	/**
	 * Adds a new entry to this heap.
	 * 
	 * @param newEntry An object to be added.
	 */
	public void add(T newEntry);

	/**
	 * Removes and returns the smallest item in this heap.
	 * 
	 * @return Either the smallest object in the heap or,
	 *         if the heap is empty before the operation, null.
	 */
	public T removeMin();

	/**
	 * Retrieves the smallest item in this heap.
	 * 
	 * @return Either the smallest object in the heap or,
	 *         if the heap is empty, null.
	 */
	public T getMin();

	/**
	 * Detects whether this heap is empty.
	 * 
	 * @return True if the heap is empty, or false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Gets the size of this heap.
	 * 
	 * @return The number of entries currently in the heap.
	 */
	public int getSize();

	/** Removes all entries from this heap. */
	public void clear();
} // end MinHeapInterface
