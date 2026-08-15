import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze{
    private final int N;
    private Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        M.addEdge(v,w);
    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
        validateVertex(v);
        validateVertex(w);
        if(v==w) return true;
        for(int adjacent : M.adj(v))
            if(adjacent == w) return true;
        for(int adjacent : M.adj(w))
            if(adjacent == v) return true;
        return false;
    }	
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
		Graph graph = new Graph(this.N*this.N);
        for(int i=0;i<this.N;i++){
            for(int j=0;j<this.N-1;j++){
                graph.addEdge(i*this.N+j,i*this.N+j+1);
            }
        }
        for(int i=0;i<this.N;i++){
            for(int j=0;j<this.N-1;j++){
                graph.addEdge(i+j*this.N,i+(j+1)*this.N);
            }
        }

        return graph;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
		Graph grid = mazegrid();
        RandomDepthFirstPaths RDFS = new RandomDepthFirstPaths(grid,this.startnode);
        RDFS.randomDFS(grid);
        int[] edges = RDFS.edge();

        for(int i=0; i<this.N*this.N;i++) {
            if(!hasEdge(i,edges[i]))
                addEdge(i,edges[i]);
        }
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){
        DepthFirstPaths DFS = new DepthFirstPaths(this.M,w);
        DFS.nonrecursiveDFS(this.M);
        int[] edges = DFS.edge();
        LinkedList<Integer> path = new LinkedList<Integer>();
        int vertex = v;

        while(vertex!=w){
            path.add(vertex);
            vertex = edges[vertex];
        }

        path.add(vertex);

        return path;
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= this.N*this.N)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.N*this.N-1));
    }

    public static void main(String[] args) {
        //Color col_dfs = new Color(50,100,200);
        //Color col_bfs = new Color(200,100,50);
        Random rnd = new Random();
        final int sideLength = 40;
        //Maze bleh = new Maze(sideLength,rnd.nextInt(sideLength*sideLength));
        Maze bleh = new Maze(sideLength,sideLength*(sideLength-1)+ (int) sideLength/2);
        //List<Integer> path = bleh.findWay(0, sideLength*sideLength-1);
        List<Integer> path = bleh.findWay(0, sideLength*sideLength-1);
        //BreadthFirstSearch bfs = new BreadthFirstSearch(bleh.M(),sideLength*sideLength-1);
        //bfs.nonrecursiveBFS(bleh.M());
        //List<Integer> pahh = bfs.pathTo(0);
        //GridGraph vis = new GridGraph(bleh.M(),path,col_dfs);
        //vis.plot(pahh,1,col_bfs);
        GridGraph dih = new GridGraph(bleh.M(),path);
    }


}

