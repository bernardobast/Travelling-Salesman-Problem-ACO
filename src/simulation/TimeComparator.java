package simulation;
import java.util.Comparator;

/**
* Override of the Comparator for the Priority Queue
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-06-09
*/


public class TimeComparator implements Comparator<IEvent> {

	@Override
	public int compare(IEvent o1, IEvent o2) {
		
		if(o1.GetTime()<o2.GetTime())
			return -1;
		else if(o1.GetTime()>o2.GetTime())
			return 1;
		return 0;
	}
	
}