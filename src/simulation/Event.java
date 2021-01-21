package simulation;

import graph.IGraph;

/**
* Event class that represents any event to be added in the graph
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/


public abstract class Event implements IEvent {
		
	protected double time;   					//Time when the event is going to be executed 
	ISimStatus s;								//SimStatus interface used for observation events 
	IGraph g;									//Graph interface

	/**
	 * Constructs the event to be added in the PEC 
	 * 
	 * @param time time when the event is going to occur 
	 * @param s SimStatus Interface
	 * @param g Graph interface 
	 */
	protected Event(double time, ISimStatus s, IGraph g) {
		this.time = time;
		this.s=s;
		this.g=g;
	}
	
	/**
	 * Getter for the current simulation time
	 * 
	 * @return Returns the current simulation time
	 */
	public double GetTime(){
		return time;
	}
	
	/**
	 * Action function that executes the event
	 * 
	 * @param currentTime the current time of the simulation
	 * @return boolean if True the event is added again in the PEC if False not
	 */
	public abstract boolean Action(double currentTime);


}
