/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name="countries")
public class Country implements Serializable {

	@Id
	@Column(name="country")
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
