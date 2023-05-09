import java.util.Iterator;

public interface ListWithIteratorInterface<T> extends ListInterface<T> {
    /** Creates an iterator that iterates through this list.
     * @return An iterator that provides sequential and ordered access to the elements in this list.
     */
    public Iterator<T> getIterator();
}
