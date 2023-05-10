import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
    private T label;
    private ListWithIteratorInterface<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;
    
    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new LinkedListWithIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }
    
    public T getLabel() {
        return label;
    }
    
    public void visit() {
        visited = true;
    }
    
    public void unvisit() {
        visited = false;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) {
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    duplicateEdge = true;
                }
            }
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }
    
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }
    
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    }
    
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }
    
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }
    
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null)) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited()) {
                result = nextNeighbor;
            }
        }
        return result;
    }
    
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }
    
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }
    
    public boolean hasPredecessor() {
        return previousVertex != null;
    }
    
    public void setCost(double newCost) {
        cost = newCost;
    }

    public double getCost() {
        return cost;
    }

    public boolean equals(Object other) {
        boolean result;
        if ((other == null) || (getClass() != other.getClass())) {
            result = false;
        } else {
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>) other;
            result = label.equals(otherVertex.label);
        }
        return result;
    }

    private class NeighborIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;
        
        private NeighborIterator() {
            edges = edgeList.getIterator();
        }
        
        public boolean hasNext() {
            return edges.hasNext();
        }
        
        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            } else {
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        }
        
        // public void remove() {
        //     edgeList.remove();
        //     //throw new UnsupportedOperationException();
        // }
    }

    private class WeightIterator implements Iterator<Double> {
        private Iterator<Edge> edges;
        
        private WeightIterator() {
            edges = edgeList.getIterator();
        }
        
        public boolean hasNext() {
            return edges.hasNext();
        }
        
        public Double next() {
            Double edgeWeight = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                edgeWeight = edgeToNextNeighbor.getWeight();
            } else {
                throw new NoSuchElementException();
            }
            return edgeWeight;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Edge {
        private VertexInterface<T> vertex;
        private double weight;
        
        private Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        }
        
        private Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
            weight = 0;
        }
        
        private VertexInterface<T> getEndVertex() {
            return vertex;
        }
        
        private double getWeight() {
            return weight;
        }
    }
}
