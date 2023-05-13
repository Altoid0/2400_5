//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//
/** An interface of methods that create, manipulate, and process a graph. */
public interface GraphInterface<T> extends BasicGraphInterface<T>, GraphAlgorithmsInterface<T> {
    /** Removes a specific edge between two vertices from this graph, if present.
     * @param begin An object that labels the beginning vertex of the edge.
     * @param end An object, distinct from begin, that labels the end vertex of the edge.
     * @return True if the edge is removed, or false if not. */
    public boolean removeEdge(T begin, T end);
} // end GraphInterface