package simulation;

import ants.*;
import graph.*;

/**
* Main class of the simulation. 
* 
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-06-09
*/
public class Main{
		
	/**
	 * Main method of the simulation. 
	 * Controls all simulation. Reads the .xml file, creates the graph, the ants and some of the events and controls them via the PEC until final instant is reached.
	 *
	 * @param args Array of Strings with the inputs of the simulation.
	 */
	public static void main(String[] args) {
	
		// Variables
		double curentTime = 0;									// Current Simulation Time
		double simulationTime = 0;								// Final Simulation Time
	
			
		// Verify Input
		if(args.length != 1){
			System.out.println("Usage: java ArtigoHandler <nome do fich.xml>");
			return;
		}
		
		// Start Simulation
		ISimulation sim = new ReadXML();
		
		// Read XML	
		sim.read(args[0]);
		
		// Validate XML
		sim.validate();

		// Gets simulation final time
		simulationTime = sim.getFinalinst();
				
		// Creates Graph
		IGraph g = new Graph(sim.getNbnodes(), sim.getNestnode(), sim.getWeightMatrix(), sim.getAlpha(), sim.getBeta(), sim.getPlevel(), sim.getDelta(), sim.getEta(), sim.getRho());
		
		// Initializes ants
		IColony colony = new Colony(sim.getAntcolsize(), sim.getNbnodes(), sim.getNestnode());
		
		
		// Initializes PEC 
		IPEC PEC = new PEC(sim.getAntcolsize(),0);
		
		// Initializes observations
		ISimStatus s = new SimStatus(simulationTime,1,0,0,100000,g);
		
		// Creates the initial move of each ant and inserts them in the PEC
		for(int i=0 ; i<sim.getAntcolsize(); i++) {
			IEvent ev = new EventAntMove(i, curentTime, sim.getNbnodes() , colony, g,s,PEC);
			PEC.addEvPEC(ev);		
		}
	
		// Simulates until final instant is reached
		while(true) {
		    PEC.nextEvPEC(curentTime);
			curentTime = PEC.getCurrentTime();
		}
	}
}
