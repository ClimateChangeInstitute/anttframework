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
	
	protected String unit;

	public MMElementDataID(String mmElement, String element, String unit) {
		super();
		this.mmElement = mmElement;
		this.element = element;
		this.unit = unit;
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
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MMElementDataID) {
			MMElementDataID other = (MMElementDataID) obj;
			return mmElement != null && element != null
					&& mmElement.equals(other.mmElement)
					&& element.equals(other.element) && unit.equals(other.unit);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
