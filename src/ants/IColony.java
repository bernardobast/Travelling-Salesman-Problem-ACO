package ants;

/**
* Interface of the ant colony
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/

public interface IColony {
	
	/**
	 * Function used to return a specific ant.
	 * 
	 * @param ant_nb ant index identifier.
	 * @return returns an ant.
	 */
	public IAnt colonySearch(int ant_nb);
}
