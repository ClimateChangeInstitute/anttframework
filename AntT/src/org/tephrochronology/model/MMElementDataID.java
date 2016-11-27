/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

/**
 * @author Mark Royer
 *
 */
public class MMElementDataID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String mmElement;

	protected String element;

	public MMElementDataID(String mmElement, String element) {
		super();
		this.mmElement = mmElement;
		this.element = element;
	}

	MMElementDataID() {
	}

	public String getMmElement() {
		return mmElement;
	}

	public void setMmElement(String mmElement) {
		this.mmElement = mmElement;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
	
	
}
