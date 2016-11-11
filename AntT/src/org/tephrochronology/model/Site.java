/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "sites")
public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "site_id")
	private String siteID;

	@JoinColumn(name = "site_type")
	private SiteType siteType;

	/**
	 * -90 <= latitude <= 90
	 */
	@Column(name = "latitude")
	private float latitude;

	/**
	 * -180 <= longitude <= 180
	 */
	@Column(name = "longitude")
	private float longitude;

	/**
	 * Meters
	 */
	@Column(name = "elevation_m")
	private float elevation;

	@Column(name = "comment")
	private String comment;

	/**
	 * @param siteID
	 * @param siteType
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 * @param comment
	 */
	public Site(String siteID, SiteType siteType, float latitude,
			float longitude, float elevation, String comment) {
		super();
		this.siteID = siteID;
		this.siteType = siteType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.comment = comment;
	}

	public Site() {
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getElevation() {
		return elevation;
	}

	public void setElevation(float elevation) {
		this.elevation = elevation;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
