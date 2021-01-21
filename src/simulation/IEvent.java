package simulation;

/**
* Interface for the events added in the pec class 
* 
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/

public interface IEvent {
	
	/**
	 * Function that represents the action performed by each event.
	 * 
	 * @param currentTime current simulation time.
	 * @return boolean if a new event must be added (TRUE) or not (FALSE).
	 */
	public abstract boolean Action(double currentTime);
	
	/**
	 * Getter for the current simulation time.
	 * 
	 * @return Returns the current simulation time.
	 */
	public double GetTime();
	
}

