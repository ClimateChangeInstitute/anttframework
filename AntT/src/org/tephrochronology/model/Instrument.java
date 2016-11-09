/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Instrument {

	private String id;
	
	private String longName;
	
	private String location;
	
	private String comment;

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
