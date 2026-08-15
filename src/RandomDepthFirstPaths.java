import java.util.*;

public class RandomDepthFirstPaths {
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
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }

    public void randomDFS(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Arrays.fill(marked, false);
        edgeTo[s]=s;
        for(int i=0;i<G.V();i++){
            Collections.shuffle(G.adj(i),new Random());
        }
        randomDFS(G, s);
    }

    // depth first search from v
    private void randomDFS(Graph G, int v) {
        marked[v]=true;
        for(int w : G.adj(v)) {
            if(!marked[w]) {
                marked[w]=true;
                edgeTo[w] = v;
                randomDFS(G,w);
            }
        }
    }

    public void randomNonrecursiveDFS(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Arrays.fill(marked, false);
        for(int i=0; i<G.V();i++) {
            Collections.shuffle(G.adj(i),new Random());
        }
        Iterator<Integer>[] adjacentIter = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int i=0; i<G.V(); i++) {
            adjacentIter[i] = G.adj(i).iterator();
        }
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        edgeTo[s] = s;
        stack.push(s);
        while(!stack.isEmpty()){
            int parent = stack.peek();
            marked[parent] = true;
            if(adjacentIter[parent].hasNext()){
                int child = adjacentIter[parent].next();
                if(!marked[child]) {
                    marked[child] = true;
                    edgeTo[child] = parent;
                    stack.push(child);
                }
            } else {
                marked[parent]=true;
                stack.pop();
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
