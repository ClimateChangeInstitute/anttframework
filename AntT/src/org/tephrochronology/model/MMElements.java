/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used for complete {@link MMElement} XML results.
 * 
 * @author Mark Royer
 *
 */
@XmlRootElement(name = "mmelements")
public class MMElements {

	/**
	 * 
	 */
	@XmlElement(name = "mmelement")
	List<MMElement> mmelements;

	public MMElements() {
	}

	public MMElements(List<MMElement> mmelements) {
		super();
		this.mmelements = mmelements;
	}

	public List<MMElement> getMmelements() {
		return mmelements;
	}

	public void setMmelements(List<MMElement> mmelements) {
		this.mmelements = mmelements;
	}
}
