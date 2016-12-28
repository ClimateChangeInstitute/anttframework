/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "units")
public class Unit implements Serializable, Comparable<Unit> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "unit")
	@XmlValue
	protected String unit;

	public Unit(String unit) {
		super();
		this.unit = unit;
	}

	public Unit() {
		super();
		unit = "";
	}

	public String getName() {
		return unit;
	}

	@Override
	public int compareTo(Unit o) {
		return unit.compareTo(o.unit);
	}

	@Override
	public int hashCode() {
		return unit.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return unit.equals(((Element) obj).name);
	}

}
