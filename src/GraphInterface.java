/** An interface of methods that create, manipulate, and process a graph. */
public interface GraphInterface<T> extends BasicGraphInterface<T>, GraphAlgorithmsInterface<T> {
    public boolean removeEdge(T begin, T end);
} // end GraphInterface