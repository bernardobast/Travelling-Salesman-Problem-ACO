package simulation;

/**
* Interface to deal with the .xml file with the input parameters of the simulation.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public interface ISimulation {
	
	/**
	 * Getter for the input parameter finalinst.
	 *
	 * @return Returns parameter finalinst.
	 */
	public	double	getFinalinst();		
	
	/**
	 * Getter for the input parameter antcolsize.
	 *
	 * @return Returns parameter antcolsize.
	 */
	public	int getAntcolsize();	
	
	/**
	 * Getter for the input parameter plevel.
	 *
	 * @return Returns parameter plevel.
	 */
	public 	double	getPlevel();		
	
	/**
	 * Getter for the input parameter nbnodes.
	 *
	 * @return Returns parameter nbnodes.
	 */
	public 	int getNbnodes();	
	
	/**
	 * Getter for the input parameter nestnode.
	 *
	 * @return Returns parameter nestnode.
	 */
	public 	int getNestnode();		
	
	/**
	 * Getter for the input parameter alpha.
	 *
	 * @return Returns parameter alpha.
	 */
	public 	double 	getAlpha();			
	
	/**
	 * Getter for the input parameter beta.
	 *
	 * @return Returns parameter beta.
	 */
	public 	double 	getBeta();				
	
	/**
	 * Getter for the input parameter delta.
	 *
	 * @return Returns parameter delta.
	 */
	public 	double 	getDelta();				
	
	/**
	 * Getter for the input parameter rho.
	 *
	 * @return Returns parameter rho.
	 */
	public	double	getRho();			
	
	/**
	 * Getter for the input parameter eta.
	 *
	 * @return Returns parameter eta.
	 */
	public	double	getEta();			
	
	/**
	 * Getter for the matrix with the weight of all the edges of the graph.
	 *
	 * @return Returns the weight matrix.
	 */
	public  int[][]	getWeightMatrix();   
	
	/**
	 * Reads the .xml file and saves the values in the respective variables.
	 *
	 * @param args String with the name of the .xml input file.
	 */
	public void  read (String args);
	
	/**
	 * Validates the values received as input in the .xml file.
	 * All values must be equal or greater than zero (except the edges weights that can be zero when doesn't exists connection).
	 */
	public void  validate ();	
}