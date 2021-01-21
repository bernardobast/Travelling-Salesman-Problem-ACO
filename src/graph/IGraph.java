package graph;

import java.util.ArrayList;

/**
* Interface for the Graph of the simulation.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public interface IGraph {
	
	/**
	 * Gets the next node for the ant to travel.
	 *
	 * @param path The ant path.
	 * @return Returns the index of the next node.
	 */
	public int getNextPath(ArrayList<Integer> path);	
	
	/**
	 * Add pheromones to the graph.
	 * Realized once the Hammiltonian path is finished.
	 *
	 * @param path The ant path.
	 */
	public void addPheromones(ArrayList<Integer> path);
	
	/**
	 * Getter for the time needed to traverse a specific edge.
	 *
	 * @param current_node The ant current node.
	 * @param next_node The ant next node.
	 * @return Returns the time to traverse the edge.
	 */
	public double travTime(int current_node, int next_node);	
	
	/**
	 * Calculates the total cost of the Hamiltonian cycle realized by an ant.
	 *
	 * @param path The ant path.
	 * @return Returns the cost (weight) of the path.
	 */
	public int total_path_weight(ArrayList<Integer> path);								
	
	/**
	 * Executes the evaporation event.
	 * The level of pheromones is reduced by rho units.
	 *
	 * @param n1 Index of node 1.
	 * @param n2 Index of node 2.
	 * @return Returns true if there are still a positive level of pheromones in the edge and false otherwise.
	 */
	public boolean evaporationPheromones(int n1, int n2);
	
	/**
	 * Getter for the evaporation time.
	 *
	 * @return Returns the evaporation time.
	 */
	public double evapTime();						
	
	/**
	 * Verifies if a certain edge between nodes 1 and 2 has a positive level of pheromones.
	 *
	 * @param n1 Index of node 1.
	 * @param n2 Index of node 2.
	 * @return Returns true if the level of pheromones is positive and false otherwise.
	 */
	public boolean check_pheromone(int n1,int n2);
	
	/**
	 * Getter for the nest node.
	 *
	 * @return Returns the nest node.
	 */
	public int nest_node();
}