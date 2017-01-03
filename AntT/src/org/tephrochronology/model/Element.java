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
@Table(name = "elements")
public class Element implements Serializable, Comparable<Element> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "element_name")
	protected String name;

	@Column(name = "format")
	protected String format;

	public Element(String name, String format) {
		super();
		this.name = name;
		this.format = format;
	}

	public Element() {
		super();
	}

	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}

	@Override
	public int compareTo(Element o) {
		return (name + format).compareTo((o.name + o.format));
	}

	@Override
	public int hashCode() {
		return (name + format).hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Element) {
			Element other = (Element) obj;
			return name.equals(other.name) && format.equals(other.format);
		} else
			return false;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}