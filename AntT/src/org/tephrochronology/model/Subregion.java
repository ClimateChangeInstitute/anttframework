/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Subregion {
	
	private String name;
	
	private Country country;

	public Subregion(String name, Country country) {
		super();
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	
}
