package simulation;

import java.util.ArrayList;

/**
* Interface for the observations
* 
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public interface ISimStatus {
	
	/**
	 * Prints an observation the time (n_observation*final_inst)/20. 
	 * In this print the attributes that are printed are:
	 * the number of the observation (n_observation);
	 * the time instance of the observation;
	 * the number of ant moves (n_antmoves); 
	 * the number of evaporation events (n_evaporation);
	 * the best hamiltonian cycle at the time.
	 *
	 * @param time The time of the next event to happen
	 */
	public void printStatus(double time);
	
	/**
	 * Increments the number of ant move events
	*/
	public void antmove ();
	
	/**
	 * Increments the number of evaporation events
	*/
	public void evap ();
	

	/**
	 * Checks if the new Hamilton path is better than the one stored and if so updates it.
	 *
	 * @param path List of Hamilton path nodes.
	*/
	public void hamilton_eval(ArrayList<Integer> path);
	
}
