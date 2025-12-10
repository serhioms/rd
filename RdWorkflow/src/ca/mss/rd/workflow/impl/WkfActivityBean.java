/**
 * 
 */
package ca.mss.rd.workflow.impl;


/**
 * @author mss
 *
 */
public class WkfActivityBean {

	private String Id = "";
	private String Name;
	private String Descr;
	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the descr
	 */
	public String getDescr() {
		return Descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		Descr = descr;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId();
	}


}
