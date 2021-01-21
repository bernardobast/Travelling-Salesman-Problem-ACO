package simulation;

import java.util.PriorityQueue;

/**
* Priority Queue of Events. The events are organize in decrescent order of time. 
* An Event can either be an Ant Movement or and Pheromone Evaporation.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-06-09
*/
public class PEC implements IPEC {
	
	private PriorityQueue<IEvent> pec;					// Pec queue 
    private double current_time;						// current simulation time

    /**
     * PEC class constructor used to initialize the pec. 
     * The starting time of the simulation.
     * 
     * @param n_ants number of ants in the colony.
     * @param start_time strarting time of the simulation. 
     */
	public PEC(int n_ants, double start_time) {
		pec = new PriorityQueue<IEvent>(n_ants,new TimeComparator());
		this.current_time = start_time;
	}
	
	/**
	 * Adds new Event interface to the Priority Queue PEC.
	 *
	 * @param ev An Interface of Event.
	 */
	public void addEvPEC(IEvent ev){
		pec.add(ev);
	}
	
	/**
	 * Removes and Event Interface from the Queue, updates simulation time,
	 * simulates the action of the event and if needed adds a new event to the queue with a new time stamp.
	 * 
	 * @param currentTime Current simulation time.
	 */
	
	 public void nextEvPEC(double currentTime){
		 IEvent e;
		 e=pec.remove();
		 current_time=e.GetTime();
		 if(e.Action(current_time)) {
			 pec.add(e);
		 }
	 }
	 
	 /**
	  * Getter of current simulation time.
	  * 
	  * @return current time.
	  */
	 public double getCurrentTime()
	 {
		 return current_time;
	 }
}
