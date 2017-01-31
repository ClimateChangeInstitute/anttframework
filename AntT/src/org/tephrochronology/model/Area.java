/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * Represents a location where multiple sites may appear. For example, Erebus.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "areas")
public class Area {

	@Id
	@Column(name = "area")
	private String area;

	@Column(name = "comment")
	private String comment;

	/**
	 * An Area may have many sites, and a site may be used by many
	 * areas.
	 */
	@ManyToMany
	@JoinTable(name = "areas_sites", joinColumns = {
			@JoinColumn(name = "area") }, inverseJoinColumns = {
					@JoinColumn(name = "site_id") })
	@XmlInverseReference(mappedBy = "areas")
	private List<Site> sites;

	/**
	 * 
	 */
	public Area() {
	}

	/**
	 * @param area
	 *            The name of the area (Not null)
	 * @param comment
	 *            Some meta information of the area (Null possible)
	 * @param sites
	 *            Sites in this area (Null possible)
	 */
	public Area(String area, String comment, List<Site> sites) {
		super();
		this.area = area;
		this.comment = comment;
		this.sites = sites;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

}
