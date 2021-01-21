package ants;

import java.util.ArrayList;
import java.util.Iterator;

/**
* Class that represents the ant moving through the graph storing the path and checking if an Hamilton path was found.
* If an hamiltonian path is found a boolean variable is returned with the value True, if not False is returned. 
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public class Ant implements IAnt{
		
	private int nest;							//stores the nest node 
	private int nbnodes;						//number of nodes of the graph
	private ArrayList<Integer> path;			//list where the path stored by the each ant
	private int current_node;					//current node the ant is in
	private int[] visitedNodes;					//vector that represents nodes that where visited, set to -1 every time a node is visited. Each index represents a node
	
	/**
	 * Constructs an ant assigning the number of nodes, the nest node.
	 * The path is initialized and the nest node is added to it.
	 * The visited nodes vector is initialized marking the nest node as visited.
	 *
	 * @param nbnodes number of nodes in the graph.
	 * @param nestnode the nest node.
	*/	
	public Ant(int nestnode, int nbnodes) {
		this.nbnodes = nbnodes;
		this.visitedNodes = new int[nbnodes];
		this.path = new ArrayList<Integer>();	//list that saves the path of each ant 
		this.nest = nestnode-1;					
		path.add(nest);							//nest node is added to the path
		this.current_node=nest;
		visitedNodes[current_node]=-1;
	}

	/**
	 * Function used to store the next node in the ant path and also checks if Hamilton is reached.
	 * The function also checks if a cycle was reached. If the cycle is not Hamilton it calls the function correctCycle to correct it.
	 *
	 * @param nextNode the next node of the path.
	 * @return boolean if the value is True an Hamilton was reached if not returns False.
	 */
	public boolean antMove(int nextNode) {
		visitedNodes[nextNode] = -1;
		if(checkCicle(path, nextNode)) {
			if(checkHamiltonian(nextNode)) {
				
			}
			else {
				//Cycle needs correction
				correctCycle(path, nextNode);
				current_node=nextNode;
				updatesPath();
			}
		}
		else {
			path.add(nextNode);
			current_node=nextNode;
		}
		return checkHamiltonian(nextNode);
	}
	
	/**
	 * Function used to correct the path if a non Hamiltonian Cycle is reached.
	 *
	 * @param path the current path of the ant.
	 * @param nextNode the next node of the path.
	 */
	private void correctCycle(ArrayList<Integer> path,int nextNode) {
		for(int i=path.size()-1;i>0;i--) {
			//If the node has already been visited, then its a cycle
			if(path.get(i)!=nextNode) {
				path.remove(i);
			}
			else {
				break;
			}
		}
		//s.antMoveBack();
	}
	
	/**
	 * Function used to check if a cycle was reached.
	 *
	 * @param path the current path of the ant.
	 * @param nextNode the next node of the path.
	 * @return boolean if True a cycle was found, if not returns false.
	*/
	private boolean checkCicle(ArrayList<Integer> path,int nextnode) {
		Iterator<Integer> itr =  path.iterator();
		while(itr.hasNext()) {
			int node = itr.next();
			//If the node has already been visited, then its a cycle
			if(node==nextnode) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Function used to update the visited nodes vector once a cycle is found.
	*/
	private void updatesPath() {
		resetPath();
		Iterator<Integer> itr = path.iterator();
		while(itr.hasNext()) {
			int node = itr.next();
			visitedNodes[node]=-1;
		}
	}
	
	/**
	 * Function used to reset the visited nodes vector, setting all nodes as non visited.
	 *
	*/
	private void resetPath() {
		for(int i=0; i<nbnodes;i++) {
			visitedNodes[i]=0;
		}
	}
	
	/**
	 * Checks if the cycle reached is Hamiltonian or not.
	 * 
	 * @param nextNode.
	 * @return boolean if an Hamilton is reached True is returned, if not False is returned.
	 */
	private boolean checkHamiltonian(int nextNode) {
		if(path.size()== (nbnodes)  && nextNode == (nest)) return true;
		return false;
	}
	
	/**
	 * Function used to reset the ant once an Hamiltonian path is reached.
	 * The path list is cleared and the nest node added.
	 * The visited nodes path is updated, setting the nest as the only visited node.
	 * 
	 */
	public void resetAnt() {
		path.clear();
		resetPath();
		path.add(nest);							//nest node is added to the path
		current_node=nest;
		visitedNodes[current_node]=-1;
	}
	
	/**
	 * Getter used to return the path of the ant. 
	 * 
	 * @return path of the ant. 
	 */
	public ArrayList<Integer> getPath() {
		return path;
	}	
		
	/**
	 * Getter used to return the current node of the ant.
	 * 
	 * @return returns the current node of the ant.
	 */
	public int getCurrentNode(){
		return current_node;
	}
	
}

