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

	protected String symbol;
	
	protected String unit;

	public MMElementDataID(String mmElement, String symbol, String unit) {
		super();
		this.mmElement = mmElement;
		this.symbol = symbol;
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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
			return mmElement != null && symbol != null
					&& mmElement.equals(other.mmElement)
					&& symbol.equals(other.symbol) && unit.equals(other.unit);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
