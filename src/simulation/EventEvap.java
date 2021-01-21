package simulation;

import graph.IGraph;

/**
* Event that represents one evaporation for each edge whose pheromone level is bigger than 0
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/

public class EventEvap extends Event {
	
	private int n1;						//node 1 of the edge
	private int n2;						//node 2 of the edge
	private double final_time;			//final instance in which the event ends

	/**
	 * Constructs the event in which a specific edge suffers an evaporation.
	 * There nodes n1 and n2 are assigned to the class attributes which represent the edge. 
	 * The final instance in which the event ends is also calculated.
	 *
	 * @param time the current time of the simulation.
	 * @param n1 node 1 concerning the edge to be evaporated.
	 * @param n2 node 2 concerning the edge to be evaporated.
	 * @param s interface of the class SimStatus.
	 * @param g interface of the class graph.
	 */
	public EventEvap(double time,int n1,int n2,ISimStatus s, IGraph g) {
		super(time,s,g);
		this.n1=n1;
		this.n2=n2;
		this.final_time = time+g.evapTime();
		super.time=final_time;
	}
	
	 /** 
	 * The action of evaporating one specific edge.
	 *
	 * @param currentTime the current time of the simulation.
	 * @return boolean variable that indicates if the PEC needs to add a new event (True) or not (False).
	 */
	@Override
	public boolean Action(double currentTime) {
		//Before anything if current time > (n*tau)/20 then must print status
		s.evap();
		final_time = currentTime+g.evapTime();
		super.time= final_time;
		boolean test =g.evaporationPheromones(n1, n2);
		return test; 		//If true needs another evaporation  if false it does not 
	}
	
}
