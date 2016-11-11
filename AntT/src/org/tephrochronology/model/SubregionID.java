/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

/**
 * @author Mark Royer
 *
 */
public class SubregionID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	String country;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SubregionID) {
			SubregionID other = (SubregionID) obj;
			return name != null && country != null && name.equals(other.name)
					&& country.equals(other.country);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
