package simulation;

import java.util.ArrayList;

import ants.IColony;
import graph.IGraph;

/**
* Event that represents one step of an ant through the graph. The ant chooses a random node of the graph
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/

public class EventAntMove extends Event {
	
	private int ant;					// index of the ant that is currently moving
	private int next_node;				// next node where the ant is moving
	private double final_time;			// the final instance in which the event ends
	IColony col;						// the colony interface that stores the ants
	IPEC pec;							// the PEC interface
	
	/**
	 * Constructs the event Ant Move for the ant with index ant. 
	 * The next node and the final time of the event are calculated using the colony and graph interfaces.
	 * The PEC interface is also assigned to a variable.
	 * 	
	 * @param ant the index of the ant that is currently moving.
	 * @param time the current time of the simulation.
	 * @param nb_nodes the number of nodes of the graph.
	 * @param col the colony interface where the ants are stored.
	 * @param g the graph interface.
	 * @param s the interface of the class SimStatus.
	 * @param pec the pec Interface.
	*/
	public EventAntMove(int ant, double time, int nb_nodes, IColony col, IGraph g, ISimStatus s, IPEC pec) {
		super(time,s,g);
		this.ant = ant;
		this.col = col;
		this.pec = pec;
		this.next_node = g.getNextPath(col.colonySearch(ant).getPath());
		this.final_time = time+g.travTime(col.colonySearch(ant).getCurrentNode(), next_node);
		super.time=final_time;
	}
	
	/**
	 * The action of the ant moving one step in the graph .
	 * If an Hamilton is reached, pheromones are added to the the path.
	 * Evaporation Events are added to each pheromones.
	 *
	 * @param currentTime the current time of the simulation.
	 * @return boolean variable that indicates if the PEC needs to add a new event (True) or not (False). In this case always.
	 */
	@Override
	public boolean Action(double currentTime) {	
	
		s.printStatus(currentTime);	
		s.antmove();
		
		boolean Hamiltoncheck = col.colonySearch(ant).antMove(next_node);
		
		if(Hamiltoncheck){
			col.colonySearch(ant).getPath().add(g.nest_node()-1);
			s.hamilton_eval(col.colonySearch(ant).getPath());
			addEvapEvents(col.colonySearch(ant).getPath(),currentTime);
			g.addPheromones(col.colonySearch(ant).getPath()); 					
			
			col.colonySearch(ant).resetAnt();
		}

		time = currentTime;
		next_node = g.getNextPath(col.colonySearch(ant).getPath());
		final_time = time+g.travTime(col.colonySearch(ant).getCurrentNode(), next_node);
		super.time=final_time;
		return true;
	}
	
	/**
	 * The action of the ant moving one step in the graph. 
	 *
	 * @param path the path stored by each ant.
	 * @param currentTime the current time of the simulation.
	 */
	protected void addEvapEvents(ArrayList <Integer> path,double currentTime) {
		int i = 0;
		//Adds one evaporation for each edge
		for(int j = 1; i<path.size()-1; j++) {
		//	System.out.println("j: "+j);
			
			if(g.check_pheromone(path.get(i), path.get(j)))
			{
				IEvent Evaporation = new EventEvap(currentTime, path.get(i), path.get(j),s,g);
				pec.addEvPEC(Evaporation);
			}
			i++;
		}
	}
}
