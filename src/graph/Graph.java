package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
* Provides a weighted graph with time to traverse between nodes and pheromone levels in the edges that can increase or evaporate.
* It also chooses the next node of the path based on the current node.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public class Graph implements IGraph{

	private int nb_nodes;											// number of nodes in the graph
	private int nestNode;											// the nest node
	private double alpha;											// parameter concerning the ant move event
	private double beta;										    // parameter concerning the ant move event
	private double plevel;											// parameter concerning pheromone level
	private double eta; 											// parameter concerning the pheromone evaporation event
	private double rho;												// parameter concerning the pheromone evaporation event
	private int W;													// graph total weight
	private int [][] weightmatrix;									// weigh of traversing between nodes
	private double [][] feromonas;									// pheromone levels between nodes
	private double [][] traverseTime;								// time to traverse between nodes
	private Random rand = new Random(System.currentTimeMillis());	// random variable to calculate next path
	private static Random random = new Random();					// Exponential Random Variable
	
	/**
	 * Constructs a Graph with a specific number of nodes and a specific nest node.
	 * There are weights of traversing between nodes and a time associated to each transition.
	 * The graph have some parameters concerning the pheromone levels, the move event and the evaporation event.
	 *
	 * @param nb_nodes number of nodes in the graph
	 * @param nestNode the nest node
	 * @param weightMatrix weigh of traversing between nodes
	 * @param alpha parameter concerning the ant move event  
	 * @param beta parameter concerning the ant move event
	 * @param plevel parameter concerning pheromone level
	 * @param delta parameter concerning the ant move event
	 * @param eta parameter concerning the pheromone evaporation event
	 * @param rho parameter concerning the pheromone evaporation event
	*/
	public Graph(int nb_nodes, int nestNode, int[][] weightMatrix, double alpha, double beta, double plevel, double delta, double eta, double rho) {
		this.plevel = plevel;
		this.beta = beta;
		this.alpha = alpha;
		this.nb_nodes = nb_nodes;
		this.nestNode = nestNode;
		this.eta = eta;
		this.rho = rho;
		this.weightmatrix = weightMatrix;
		this.feromonas = new double [nb_nodes][nb_nodes];				// At the beginning, the pheromone level is zero
		this.traverseTime = new double[nb_nodes][nb_nodes];				// Time Matrix to traverse an edge 
		for(int i = 0; i<weightmatrix.length; i++) {					// Traverse Time is always the same-
			for(int j=i; j<weightmatrix.length; j++) {
				W += weightmatrix[i][j];
				traverseTime[i][j] = delta *weightmatrix[i][j];
				traverseTime[j][i] = delta *weightmatrix[i][j];
			}
		}
	}
	
	/**
	 * Gets the next node for the ant to travel.
	 *
	 * @param path The ant path.
	 * @return Returns the index of the next node.
	 */
	public int getNextPath(ArrayList<Integer> path) {
		Iterator<Integer> itr = path.iterator();		// iterator for the path
		int currentNode;								// the current node the ant is
		int possiblePaths=0;							// possible paths for the ant
		int [] J;										// index of possible paths 
		double [] cPartial;								// probability of each node connection 
		double [] prob;									// probability of moving for each adjacent node
		double cTotal = 0;								// total probability 									
		int aux = 0;									// auxiliary variable 
		int[] visitedNodes= new int [nb_nodes];			// Array of visited Nodes
		
		// obtain the nodes that the ant already visited
		while(itr.hasNext()) {
			int node = itr.next();
			visitedNodes[node]=-1;
		}
		
		// Current node of the ant 
		currentNode = path.get(path.size()-1);
		// Checks the possible non-visited paths 
		for(int i=0; i<weightmatrix.length; i++) {
			if(visitedNodes[i]!=-1 && weightmatrix[currentNode][i]!=0) {
				possiblePaths++;						// Possible paths
			}
		}
		
		// case there are path available not visited yet
		if(possiblePaths != 0) {	
			// Get vector J -> possible non visited nodes
			J = new int [possiblePaths];
			// Iterates through the edges and finds non visited nodes
			for(int i=0; i<weightmatrix.length; i++) {
				// Possible paths
				if(visitedNodes[i]!=-1 && weightmatrix[currentNode][i]!=0) {
					J[aux]=i;
					aux++;
				}
			}
			
			// Resets aux 
			aux = 0;
			// Calculates cParcial and cTotal
			cPartial = new double[possiblePaths];
			for(int i = 0; i<cPartial.length; i++){
					cPartial[i] = (alpha + feromonas[currentNode][J[i]]) / (beta + weightmatrix[currentNode][J[i]]);
					cTotal += cPartial[i];
					aux ++;
			}
			
			// Calculates the probability
			prob = new double[possiblePaths];
			for(int i = 0; i<prob.length; i++) {
				prob[i] = cPartial[i]/cTotal;
			}
			// Chooses Path
			return choosePath(J,prob);
			
		} else {       // case all available nodes have already been visited
			
			// All edges are possible 
			for(int i=0; i<weightmatrix.length; i++) {
				// Possible paths
				if(weightmatrix[currentNode][i]!=0) {
					possiblePaths++;
				}
			}
						
			// Resets aux
			aux = 0;
			
			// All edges of J are possible
			J = new int [possiblePaths];
			for(int i=0; i<weightmatrix.length; i++) {				
				// All of the connected nodes are possible paths
				if(weightmatrix[currentNode][i]!=0) {
					J[aux]=i;
					aux++;
				}	
			}
			// uniform distribution, all paths have same probability
			prob = new double[possiblePaths];
			for(int i = 0; i<J.length; i++) {
				prob[i] = 1.0/possiblePaths;
			}
			
			// Chooses Path
			return choosePath(J,prob);	
		}		
	}
	
	/**
	 * Chooses the next path for the ant to travel.
	 *
	 * @param J Index of possible paths .
	 * @param prob Probability of moving for each adjacent node.
	 * @return Returns the index of the chosen node.
	 */
	private int choosePath(int []J, double [] prob) {
		double random;										// random number for path choosing	
		double cumulativeProb = 0.0;						// cumulative probability
		int pathChosen=0;									// the next node
		
		// random number between 0 and 1 
		random = rand.nextDouble();
		
		// along all possible nodes, if the cumulative prob is bigger than the random number that node is selected
		for(int i = 0; i<J.length;i++) {
			cumulativeProb += prob[i];		
			if(random<=cumulativeProb) {
				pathChosen=J[i];
				return pathChosen;
			}
		}
		return 0;
	}
	
	/**
	 * Add pheromones to the graph.
	 * Realized once the Hammiltonian path is finished.
	 *
	 * @param path The ant path.
	 */
	public void addPheromones(ArrayList<Integer> path) {
		Iterator<Integer> itr = path.iterator();
		Iterator<Integer> itr2 = path.iterator();
		int denominator = 0; 
		double numerator = plevel*W;
		double addPheromones = 0;
		
		int i = 0;
		for(int j = 1; i<path.size()-1; j++) {
			denominator += weightmatrix[path.get(i)][path.get(j)];
			i++;
		}
		
		addPheromones=numerator/denominator;
		
		itr = path.iterator();			//Reset Iterator
		int node2 = itr2.next();
		int node;
		
		while(itr2.hasNext() && itr.hasNext()) {
			node = itr.next();
			node2 = itr2.next();
			feromonas[node][node2] += addPheromones; 
			feromonas[node2][node] += addPheromones;
		}
	}
	
	/**
	 * Calculates the value of an exponential distribution with mean m.
	 *
	 * @param m The mean value.
	 * @return Returns the exponential distribution value.
	 */
	private static double expRandom(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}
	
	/**
	 * Getter for the time needed to traverse a specific edge.
	 *
	 * @param current_node The ant current node.
	 * @param next_node The ant next node.
	 * @return Returns the time to traverse the edge.
	 */
	public double travTime(int current_node, int next_node) {
		double m = traverseTime[current_node][next_node];
		return expRandom(m);
	}
	
	/**
	 * Calculates the total cost of the Hamiltonian cycle realized by an ant.
	 *
	 * @param path The ant path.
	 * @return Returns the cost (weight) of the path.
	 */
	public int total_path_weight(ArrayList<Integer> path)
	{
		int tot=0;					// total weight of the ant path
	
		int j=0;
		for(int i=1;i<path.size();i++) {
			tot=tot+weightmatrix[path.get(j)][path.get(i)];
			j++;
		}
		return tot;
	}
	
	/**
	 * Executes the evaporation event.
	 * The level of pheromones is reduced by rho units.
	 *
	 * @param n1 Index of node 1.
	 * @param n2 Index of node 2.
	 * @return Returns true if there are still a positive level of pheromones in the edge and false otherwise.
	 */
	public boolean evaporationPheromones(int n1, int n2) {
		 feromonas[n1][n2] -= rho;
		 feromonas[n2][n1] -= rho;
		 if(feromonas[n1][n2]<=0) {
			 feromonas[n1][n2] = 0;
			 feromonas[n2][n1] = 0;
			 return false;				// no need for another evaporation
		 }
		 return true;					// still a positive level of pheromones, needs another evaporation
	}
	
	/**
	 * Getter for the evaporation time.
	 *
	 * @return Returns the evaporation time.
	 */
	public double evapTime() {
		return expRandom(eta);
	}
	
	/**
	 * Verifies if a certain edge between nodes 1 and 2 has a positive level of pheromones.
	 *
	 * @param n1 Index of node 1.
	 * @param n2 Index of node 2.
	 * @return Returns true if the level of pheromones is positive and false otherwise.
	 */
	public boolean check_pheromone(int n1,int n2){
		if(feromonas[n1][n2]<=0)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * Getter for the nest node.
	 *
	 * @return Returns the nest node.
	 */
	public int nest_node() {
		return nestNode;
	}
	
}	
