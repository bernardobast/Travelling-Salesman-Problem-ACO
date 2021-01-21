package simulation;

/**
* Interface for the pec 
* 
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/

public interface IPEC {

	/**
	 * Adds new Event interface to the Priority Queue PEC.
	 *
	 * @param ev An Interface of Event.
	 */
	public void addEvPEC(IEvent ev);
	
	/**
	 * Removes and Event Interface from the Queue, updates simulation time,
	 * simulates the action of the event and if needed adds a new event to the queue with
	 * a new time stamp.
	 * 
	 * @param currentTime Current simulation time.
	 */
	public void nextEvPEC(double currentTime);
	
	/**
	 * Getter of current simulation time.
	 * 
	 * @return current time.
	 */
	public double getCurrentTime();
}
