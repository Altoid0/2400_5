import java.util.Iterator;

public class Directedgraph<T> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;
    
    public Directedgraph() {
        vertices = new HashedDictionary<>();
        edgeCount = 0;
    }
    
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null;
    }
    
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        } else {
            throw new IllegalArgumentException("One or both of the vertices are not valid.");
        }

        if (result) {
            edgeCount++;
        }
        return result;
    }
    
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }
    
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    found = true;
                }
            }
        }
        return found;
    }
    
    public boolean isEmpty() {
        return vertices.isEmpty();
    }
    
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    }
    
    public int getNumberOfVertices() {
        return vertices.getSize();
    }
    
    public int getNumberOfEdges() {
        return edgeCount;
    }
    
    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new Queue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new Queue<>();
        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originVertex);
        while (!vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                }
            }
        }
        return traversalOrder;
    }

    public QueueInterface<T> getDepthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new Queue<>();
        StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();
        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexStack.push(originVertex);
        while (!vertexStack.isEmpty()) {
            VertexInterface<T> topVertex = vertexStack.peek();
            VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();
            if (nextNeighbor != null) {
                nextNeighbor.visit();
                traversalOrder.enqueue(nextNeighbor.getLabel());
                vertexStack.push(nextNeighbor);
            } else {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    public StackInterface<T> getTopologicalOrder() {
        resetVertices();
        StackInterface<T> vertexStack = new LinkedStack<>();
        int numberOfVertices = getNumberOfVertices();
        for (int counter = 1; counter <= numberOfVertices; counter++) {
            VertexInterface<T> nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push(nextVertex.getLabel());
        }
        return vertexStack;
    }

    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        // TODO: Queue is empty and done is false or stack is empty, can return a 0
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new Queue<>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();
        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(frontVertex.getCost() + 1);
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                }
                if (nextNeighbor.equals(endVertex)) {
                    done = true;
                }
            }
        }
        int pathLength = (int)endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while
        if (!path.isEmpty()) {
            return pathLength;
        } else {
            return -1; // no path found
        }
        
    }

    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        PriorityQueueInterface<EntryPQ> priorityQueue = new MinHeapPriorityQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        priorityQueue.add(new EntryPQ(originVertex, 0, null));
        while (!done && !priorityQueue.isEmpty()) {
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex();
            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor());
                if (frontVertex.equals(endVertex)) {
                    done = true;
                } else {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                    while (neighbors.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Double weightOfEdgeToNeighbor = edgeWeights.next();
                        if (!nextNeighbor.isVisited()) {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        }
                    }
                }
            }
        }
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        if (!path.isEmpty()) {
            return pathCost;
        } else {
            return -1; // no path found
        }
    }

    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        }
    }

    protected VertexInterface<T> findTerminal() {
        boolean found = false;
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (!found && vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            if (!nextVertex.isVisited() && nextVertex.getUnvisitedNeighbor() == null) {
                found = true;
                result = nextVertex;
            }
        }
        return result;
    }

    protected class EntryPQ implements Comparable<EntryPQ> {
        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost; // cost to nextVertex

        protected EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        }

        protected VertexInterface<T> getVertex() {
            return vertex;
        }

        protected VertexInterface<T> getPredecessor() {
            return previousVertex;
        }

        protected double getCost() {
            return cost;
        }

        protected void setCost(double cost) {
            this.cost = cost;
        }

        protected void setPredecessor(VertexInterface<T> previousVertex) {
            this.previousVertex = previousVertex;
        }

        @Override
        public int compareTo(EntryPQ otherEntry) {
            return (int)Math.signum(cost - otherEntry.cost);
        }
    }

}
