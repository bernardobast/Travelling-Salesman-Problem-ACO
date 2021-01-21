package simulation;

import java.util.ArrayList;

import graph.IGraph;

/**
* Object responsible for the storage and print of the actual values of certain variables related to the simulation.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-06-09
*/

public class SimStatus implements ISimStatus {
	
	private double final_inst;							// Time when the program ends 
	private int n_observation;							// Number of observations
	private int n_antmoves;								// Number of ant moves
	private int n_evaporation;							// Number of evaporation events
	private ArrayList<Integer> best_hamilton;			// Stores the best Hamilton path found 
	private int min_weight;								// Min weight of the best hamilton path 
	IGraph g;											// Graph Interface
	
	/**
	 * Constructs a SimStatus object with the attributes initializated as such.
	 * 
	 * @param final_inst final instance in which the simulation must end.
	 * @param n_observation the number of the actual observation of the simulation. Between 1 and 20.
	 * @param n_antmoves number of antmoves events that were simulated.
	 * @param n_evaporation number of pheromone evaporation events that were simulated  .
	 * @param min_weight minimun hamilton path weight fount at any time.
	 * @param g graph interface.
	 */
	public SimStatus(double final_inst, int n_observation, int n_antmoves, int n_evaporation, int min_weight, IGraph g) {
		this.final_inst = final_inst;
		this.n_observation = n_observation;
		this.n_antmoves = n_antmoves;
		this.n_evaporation = n_evaporation;
		this.min_weight=min_weight;
		this.g=g;	
		this.best_hamilton=null; 
		
	}
	
	/**
	 * To string function used to print best Hamilton path 
	 */
	@Override
	public String toString() {
		
		String s ="";
		
		if(best_hamilton==null) {
			s="Hamilton not found";
		}
		
		if(best_hamilton!=null) {
			s ="{";
		
			for (int i=0;i<best_hamilton.size()-1;i++) {
				s=s+(best_hamilton.get(i)+1);
				
				if(i!=best_hamilton.size()-2)
					s=s+",";
			}
			
			s=s+"}";
		}
		return "                 Hamilton cycle:                " + s;
	}

	
	/**
	 * Prints an observation at the time (n_observation*final_inst)/20. 
	 * In this print the attributes that are printed are:
	 * the number of the observation (n_observation);
	 * the time instance of the observation;
	 * the number of ant moves (n_antmoves); 
	 * the number of evaporation events (n_evaporation);
	 * the best hamiltonian cycle at the time.
	 *
	 * @param time The time of the next event to happen.
	 */
	public void printStatus(double time)
	{
		if(time>=(final_inst*n_observation)/20)
		{
			//Must print simulation status
			System.out.println("Observation "+ n_observation + " :");
			System.out.println("                 Present instant:               "+ ((final_inst*n_observation)/20));
			System.out.println("                 Number of move events:         "+ n_antmoves);
			System.out.println("                 Number of evaporation events:  "+ n_evaporation);
			System.out.println(this.toString());
			
			if(n_observation==20)
			{
				System.exit(0);
			}
			
			this.n_observation++;
		}
	}
	
	/**
	 * Increments the number of ant move events.
	 */
	public void antmove ()
	{
		this.n_antmoves++;
	}
	
	/**
	 * Increments the number of evaporation events.
	 */
	public void evap ()
	{
		this.n_evaporation++;
	}
	
	/**
	 * Checks if the new hamilton path is better than the one stored and if so updates it.
	 *
	 * @param path List of hamilton path nodes.
	 */
	public void hamilton_eval(ArrayList<Integer> path)
	{
		if(g.total_path_weight(path)<min_weight)
		{
			best_hamilton= new	ArrayList<Integer>(path);
			min_weight=g.total_path_weight(path);			
		}	
	}
}
