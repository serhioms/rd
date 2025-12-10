/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata;

/**
 * @author moskovsk
 *
 * Legal entities - At a minimum, any trade requires a processing organization to identify your organization, 
 * and a counterparty to indentify the other side of the deal. These legal entities need to be associated with 
 * contact information. Other types of legal entities may be required based on the processing required by the 
 * trades. They will be identified as needed. A legal entity is identified by their short name throughout the 
 * system.
 * 
 */
public class LegalEntitiy {

	final static public String className = LegalEntitiy.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public String shortName;
	public LegalEntitiyContactInfo contactInfo;
	

	/**
	 * 
	 */
	public LegalEntitiy(String shortName) {
		this.shortName = shortName;
	}

}


