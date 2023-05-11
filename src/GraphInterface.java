/** An interface of methods that create, manipulate, and process a graph. */
public interface GraphInterface<T> extends BasicGraphInterface<T>, GraphAlgorithmsInterface<T> {
    /** Removes a specific edge between two vertices from this graph, if present.
     * @param begin An object that labels the beginning vertex of the edge.
     * @param end An object, distinct from begin, that labels the end vertex of the edge.
     * @return True if the edge is removed, or false if not. */
    public boolean removeEdge(T begin, T end);
} // end GraphInterface