/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

/**
 * @author Mark Royer
 *
 */
public class RegionID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	SubregionID subRegion;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RegionID) {
			RegionID other = (RegionID) obj;
			return name != null && subRegion != null && name.equals(other.name)
					&& subRegion.equals(other.subRegion);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
