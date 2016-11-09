/**
 * 
 */
package org.tephrochronology.model;

/**
 * TODO This class will need additional properties to match the database.
 * 
 * @author Mark Royer
 *
 */
public class Ref {

	private String doi;

	/**
	 * @param doi
	 */
	public Ref(String doi) {
		super();
		this.doi = doi;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}
	
}
