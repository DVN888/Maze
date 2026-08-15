import java.util.*;

public class BreadthFirstSearch {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo; // edgeTo[v] = last edge on s-v path
    private final int s; // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BreadthFirstSearch(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }

    public void nonrecursiveBFS(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Arrays.fill(marked, false);
        for(int i=0; i<G.V();i++) {
            Collections.shuffle(G.adj(i),new Random());
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        marked[s] = true;
        edgeTo[s] = s;
        queue.add(s);
        while(!queue.isEmpty()){
            int parent = queue.poll();
            marked[parent] = true;
            for(int child : G.adj(parent)) {
                if(!marked[child]) {
                    marked[child] = true;
                    edgeTo[child] = parent;
                    queue.add(child);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * 
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the vertex {@code v} and the source vertex {@code s},
     * or
     * {@code null} if no such path.
     * 
     * @param v the vertex
     * @return the sequence of vertices on a path between the vertex
     *         {@code v} and the source vertex {@code s}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * 
     */
    public List<Integer> pathTo(int v) {
        if(!hasPathTo(v)) return null;

        List<Integer> path = new LinkedList<Integer>();
        int pastvertex = v;
        while (pastvertex!=this.s) {
            path.add(pastvertex);
            pastvertex = edgeTo[pastvertex];
        }
        path.add(this.s);

        return path;
    }

    public int[] edge() {
        return edgeTo;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
