/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Region {

	private String name;
	
	private Subregion subRegion;

	public Region(String name, Subregion subRegion) {
		super();
		this.name = name;
		this.subRegion = subRegion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subregion getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(Subregion subRegion) {
		this.subRegion = subRegion;
	}
	
	
}
