/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Country {

	private String name;

	public Country(String name) {
		super();
		this.name = name;
	}

	public Country() {
		super();
		name = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
