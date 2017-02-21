/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used for complete {@link Chemistry} XML results.
 * 
 * @author Mark Royer
 *
 */
@XmlRootElement(name="chemistries")
public class Chemistries {

	/**
	 * 
	 */
	@XmlElement(name = "chemistry")
	List<Chemistry> chemistries;

	public Chemistries() {
	}

	public Chemistries(List<Chemistry> mmelements) {
		super();
		this.chemistries = mmelements;
	}

	public List<Chemistry> getChemistries() {
		return chemistries;
	}

	public void setMmelements(List<Chemistry> chemistries) {
		this.chemistries = chemistries;
	}

}
