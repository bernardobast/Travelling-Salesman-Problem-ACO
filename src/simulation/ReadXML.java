package simulation;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
* Allows to read the .xml file using the SAX interface (Simple API for XML)
* It can also validate the inputs.
*
* @author  Bernardo Bastos
* @author  Francisco Contente
* @author  Valter Piedade
* @version 1.0
* @since   2019-05-09
*/
public class ReadXML extends DefaultHandler implements ISimulation{
	
	private	static 	double	finalinst;			// final instant
	private	static 	int 	antcolsize;			// ant colony size
	private static 	double	plevel;				// parameter concerning pheromone level
	private static 	int	 	nbnodes;			// number of nodes in the graph
	private static 	int 	nestnode;			// the nest node
	private static 	double 	alpha;				// parameters concerning the ant move event
	private static 	double 	beta;				// parameters concerning the ant move event
	private static 	double 	delta;				// parameters concerning the ant move event
	private	static	double	rho;				// parameters concerning the pheromone evaporation
	private	static	double	eta;				// parameters concerning the pheromone evaporation 
	private static  int[][]	weightMatrix; 		// weight matrix
	
	// File Element Names
	private static final String SIMULATION="simulation";
	private static final String GRAPH="graph";
	private static final String NODE="node";
	private static final String WEIGHT="weight";
	private static final String MOVE="move";
	private static final String EVAPORATION="evaporation";
	
	// Local attributes
	private static 	int nodeid;					// variable to keep start node index
	private static 	int target;					// variable to keep target node index
	private String read_string="";				// variable to keep the weight value
	
	/**
	 *  Get the name and the attributes of the element. 
	 *
	 * @param uri default input.
	 * @param name default input.
	 * @param qName Element name.
	 * @param atts Attributes of the element.
	 */
	public void startElement(String uri, String name, String qName, Attributes atts){
		
		// SIMULATION
		if(qName.equals(SIMULATION)) {
			// finalinst
			String finst = atts.getValue(0);
			finalinst = Double.parseDouble(finst);
			// antcolsize
			String antcol = atts.getValue(1);
			antcolsize = Integer.parseInt(antcol);
			// plevel
			String pl = atts.getValue(2);
			plevel = Double.parseDouble(pl);
		}
		
		// GRAPH
		if(qName.equals(GRAPH)) {
			// nbnodes
			String nb = atts.getValue(0);
			nbnodes = Integer.parseInt(nb);
			// initializes weight matrix
			weightMatrix = new int[nbnodes][nbnodes];
			// nestnode
			String nst = atts.getValue(1);
			nestnode = Integer.parseInt(nst);
		}
		
		// NODE
		if(qName.equals(NODE)) {
			// nodeidx
			String id = atts.getValue(0);
			nodeid = Integer.parseInt(id);
		}
		
		// WEIGHT
		if(qName.equals(WEIGHT)) {
			// target node
			String tg = atts.getValue(0);
			target = Integer.parseInt(tg);
		}
		
		// MOVE
		if(qName.equals(MOVE)) {
			// alpha
			String al = atts.getValue(0);
			alpha = Double.parseDouble(al);	
			// beta
			String bt = atts.getValue(1);
			beta = Double.parseDouble(bt);	
			// delta
			String dt = atts.getValue(2);
			delta = Double.parseDouble(dt);	
		}
		
		// EVAPORATION
		if(qName.equals(EVAPORATION)) { 
			// eta
			String et = atts.getValue(0);
			eta = Double.parseDouble(et);
			// rho
			String rh = atts.getValue(1);
			rho = Double.parseDouble(rh);
		}
		
	}
	
	/**
	 * Get the element name. 
	 *
	 * @param uri Default input.
	 * @param name Default input.
	 * @param tag Element name.
	 */
	public void endElement(String uri, String name, String tag){
		// Fills weight matrix
		if(tag.equals(WEIGHT)){
			weightMatrix[nodeid-1][target-1]=Integer.parseInt(read_string);
			weightMatrix[target-1][nodeid-1]=Integer.parseInt(read_string);
			return;
		}
	}
	
	/**
	 * Obtain the characters.
	 *
	 * @param ch Default input.
	 * @param start Default input.
	 * @param length Default input.
	 */
	public void characters(char[] ch, int start, int length){
		// Read attribute value
		read_string=new String(ch,start,length);
	}
	
	/**
	 * XML parse warning.
	 *
	 * @param e Encapsulate an XML parse warning.
	 * @throws SAXParseException Encapsulate an XML parse warning.
	 */
	public void warning(SAXParseException e)throws SAXParseException{
		System.out.println("Warning! "+ e.getMessage());
	}
	
	/**
	 * XML parse error.
	 *
	 * @param e Encapsulate an XML parse error.
	 * @throws SAXParseException Encapsulate an XML parse error.
	 */
	public void error(SAXParseException e)throws SAXParseException{
		System.out.println("Error! "+ e.getMessage());
	}
	
	/**
	 * XML parse fatal error.
	 *
	 * @param e Encapsulate an XML parse error.
	 * @throws SAXParseException Encapsulate an XML parse error.
	 */
	public void fatalError(SAXParseException e) throws SAXParseException{
		System.out.println("Fatal error! "+ e.getMessage()+"\nAbortando");
		System.exit(-1);
	}
	
	/**
	 * Reads the .xml file and saves the values in the respective variables.
	 *
	 * @param args String with the name of the .xml input file.
	 */
	public void  read (String args){
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		
		try{
			
			SAXParser parser = factory.newSAXParser();
			parser.parse(new File(args),new ReadXML());
			
		} catch(ParserConfigurationException e){
			// Bad parser configuration
			System.out.println(e);
		} catch(SAXException e){
			// Not well-formed document
			System.out.println(e);
		} catch(IOException e){
			// Error accessing to the document
			System.out.println(e);
		}	
	}
	
	/**
	 * Validates the values received as input in the .xml file.
	 * All values must be equal or greater than zero (except the edges weights that can be zero when doesn't exists connection).
	 */
	public void  validate (){
		int error=0;
		for(int i=0; i<=11; i++) {
			switch(i) {
			case 1: // finalinst
				if(finalinst<=0.0) {
					System.out.println("Invalid value for finalinst. Value must be greater than zero.");
					error=1;
				}
				break;
			case 2: // antcolsize
				if(antcolsize<=0) {
					System.out.println("Invalid value for antcolsize. Value must be greater than zero.");
					error=1;
				}
				break;
			case 3: // plevel
				if(plevel<=0.0) {
					System.out.println("Invalid value for plevel. Value must be greater than zero.");
					error=1;
				}
				break;
			case 4: // nbnodes
				if(nbnodes<=0) {
					System.out.println("Invalid value for nbnodes. Value must be greater than zero.");
					error=1;
				}
				break;
			case 5: // nestnode
				if(nestnode<=0) {
					System.out.println("Invalid value for nestnode. Value must be greater than zero.");
					error=1;
				}
				break;
			case 6: // alpha
				if(alpha<=0.0) {
					System.out.println("Invalid value for alpha. Value must be greater than zero.");
					error=1;
				}
				break;
			case 7: // beta
				if(beta<=0.0) {
					System.out.println("Invalid value for beta. Value must be greater than zero.");
					error=1;
				}
				break;
			case 8: // delta
				if(delta<=0.0) {
					System.out.println("Invalid value for delta. Value must be greater than zero.");
					error=1;
				}
				break;
			case 9: // rho
				if(rho<=0.0) {
					System.out.println("Invalid value for rho. Value must be greater than zero.");
					error=1;
				}
				break;
			case 10: // eta
				if(eta<=0.0) {
					System.out.println("Invalid value for eta. Value must be greater than zero.");
					error=1;
				}
				break;
			case 11: // weightMatrix
				for (int k = 0; k < nbnodes; k++) {
				    for (int j = k; j < nbnodes; j++) {
				        if(weightMatrix[k][j]<0) {
				        	System.out.println("Incorrect weight in the edge between nodes "+k+" and "+j+".");
							error=1;
				        }
				    }
				}
				break;
			}
		}
		// exit if exists errors in input parameters
		if(error==1) {
			System.exit(-1);
		}
	}
	
	/**
	 * Getter for the input parameter finalinst.
	 *
	 * @return Returns parameter finalinst.
	 */
	public double getFinalinst() {
		return finalinst;
	}

	/**
	 * Getter for the input parameter antcolsize.
	 *
	 * @return Returns parameter antcolsize.
	 */
	public int getAntcolsize() {
		return antcolsize;
	}

	/**
	 * Getter for the input parameter plevel.
	 *
	 * @return Returns parameter plevel.
	 */
	public double getPlevel() {
		return plevel;
	}

	/**
	 * Getter for the input parameter nbnodes.
	 *
	 * @return Returns parameter nbnodes.
	 */
	public int getNbnodes() {
		return nbnodes;
	}

	/**
	 * Getter for the input parameter nestnode.
	 *
	 * @return Returns parameter nestnode.
	 */
	public int getNestnode() {
		return nestnode;
	}
	
	/**
	 * Getter for the input parameter alpha.
	 *
	 * @return Returns parameter alpha.
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Getter for the input parameter beta.
	 *
	 * @return Returns parameter beta.
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Getter for the input parameter delta.
	 *
	 * @return Returns parameter delta.
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * Getter for the input parameter rho.
	 *
	 * @return Returns parameter rho.
	 */
	public double getRho() {
		return rho;
	}

	/**
	 * Getter for the input parameter eta.
	 *
	 * @return Returns parameter eta.
	 */
	public double getEta() {
		return eta;
	}

	/**
	 * Getter for the matrix with the weight of all the edges of the graph.
	 *
	 * @return Returns the weight matrix.
	 */
	public int[][] getWeightMatrix() {
		return weightMatrix;
	}
	
}