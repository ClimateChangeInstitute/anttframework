/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "regions")
@IdClass(RegionID.class)
public class Region implements Serializable {

	@Id
	@Column(name = "region")
	private String name;

	@Id
	@JoinColumns({
		@JoinColumn(name = "subregion", referencedColumnName = "subregion"),
		@JoinColumn(name = "country", referencedColumnName = "country")})
	private Subregion subRegion;

	public Region() {
	}

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
