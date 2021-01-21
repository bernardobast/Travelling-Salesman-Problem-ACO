package ants;

import java.util.ArrayList;

/**
* Interface of the ant class .
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public interface IAnt {	
	
	/**
	 * Function used to store the next node in the ant path and also checks if Hamilton is reached.
	 * The function also checks if a cycle was reached. If the cycle is not Hamilton it calls the function correctCycle to correct it. 
	 *
	 * @param nextNode the next node of the path.
	 * @return boolean if the value is True an Hamilton was reached if not returns False.
	*/
	public boolean antMove(int nextNode);
	
	/**
	 * Getter used to return the path of the ant. 
	 * 
	 * @return path of the ant. 
	 */
	public ArrayList<Integer> getPath(); 
	
	/**
	 * Getter used to return the current node of the ant.
	 * 
	 * @return returns the current node of the ant.
	 */
	public int getCurrentNode();
	
	/**
	 * Function used to reset the ant once an Hamiltonian path is reached.
	 * The path list is cleared and the nest node added.
	 * The visited nodes path is updated, setting the nest as the only visited node.
	 */
	public void resetAnt();	
}
