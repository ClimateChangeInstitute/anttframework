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
@Table(name = "instruments")
public class Instrument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "iid")
	private String id;

	@Column(name = "long_name")
	private String longName;

	@Column(name = "location")
	private String location;

	@Column(name = "comments")
	private String comment;

	public Instrument() {
	}

	/**
	 * @param id
	 * @param longName
	 * @param location
	 * @param comment
	 */
	public Instrument(String id, String longName, String location,
			String comment) {
		super();
		this.id = id;
		this.longName = longName;
		this.location = location;
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
