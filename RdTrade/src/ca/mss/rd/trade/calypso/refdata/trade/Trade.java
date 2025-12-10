/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata.trade;

import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.trade.calypso.environment.Property;



/**
 * @author moskovsk
 *
 * At a minimum, any trade requires a processing organization to identify your organization, 
 * and a counterparty to indentify the other side of the deal. These legal entities need to be associated with 
 * contact information. Other types of legal entities may be required based on the processing required by the 
 * trades. They will be identified as needed. A legal entity is identified by their short name throughout the 
 * system. 
 * In addition, credit derivatives trades require issuers. Defining issuers is described below.
 *  
 */
public class Trade {

	final static public String className = Trade.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public ProcessingOrganization processingOrg;
	public Counterparty counterparty;
	
	public Set<Property> properties = new HashSet<Property>();

	
}


