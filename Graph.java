import java.util.*;

class Edge {
    int target, weight;

    public Edge(int target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Node {
    int vertex, distance, edges;

    public Node(int vertex, int distance, int edges) {
        this.vertex = vertex;
        this.distance = distance;
        this.edges = edges;
    }
}

public class Graph {
    private List<List<Edge>> adjList;

    public Graph(int n) {
        adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        adjList.get(u).add(new Edge(v, weight));
    }

    public int shortestPathWithKEdges(int src, int dest, int k) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        pq.offer(new Node(src, 0, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.vertex == dest && current.edges <= k) {
                return current.distance;
            }

            if (current.edges < k) {
                for (Edge edge : adjList.get(current.vertex)) {
                    int newDist = current.distance + edge.weight;
                    int newEdges = current.edges + 1;
                    pq.offer(new Node(edge.target, newDist, newEdges));
                }
            }
        }
        
        return -1; // Path with <= k edges not found
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 1, 4);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 7);
        
        int src = 0, dest = 4, k = 3;
        int shortestPath = graph.shortestPathWithKEdges(src, dest, k);
        
        System.out.println("Shortest path with up to " + k + " edges: " + (shortestPath != -1 ? shortestPath : "No path found"));
    }
}
