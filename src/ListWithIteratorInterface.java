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

public interface ListWithIteratorInterface<T> extends ListInterface<T> {
    /** Creates an iterator that iterates through this list.
     * @return An iterator that provides sequential and ordered access to the elements in this list.
     */
    public Iterator<T> getIterator();
}
