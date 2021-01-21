package ants;

/**
* Colony class used to store every ant that move through the graph.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public class Colony implements IColony{

	private IAnt [] colony;						//Colony vector used to store ants

	/**
	 * Colony constructor. 
	 * Creates a vector of IAnts of the size of the colony.
	 * 
	 * @param col_size size of the colony.
	 * @param nb_nodes mumber of nodes of the graph.
	 * @param nest_node nest node of the graph.
	 */
	public Colony(int col_size, int nb_nodes,int nest_node) {
		this.colony = new IAnt[col_size];
		for(int i=0; i<col_size; i++) {
			this.colony[i] = new Ant(nest_node, nb_nodes);
		}
	}
	
	/**
	 * Function used to return a specific ant.
	 * 
	 * @param ant_nb ant index identifier.
	 * @return returns an ant.
	 */
	public IAnt colonySearch(int ant_nb) {
		return colony[ant_nb];
	}
	
}
