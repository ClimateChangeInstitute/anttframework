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
@Table(name = "elements")
public class Element implements Serializable, Comparable<Element> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "element_name")
	@XmlValue
	protected String name;

	public Element(String name) {
		super();
		this.name = name;
	}

	public Element() {
		super();
		name = "";
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Element o) {
		return name.compareTo(o.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return name.equals(((Element) obj).name);
	}

}