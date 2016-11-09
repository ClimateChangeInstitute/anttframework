/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Site {

	private String siteID;
	
	private SiteType siteType;
	
	/**
	 * -90 <= latitude <= 90
	 */
	private float latitude;
	
	/**
	 * -180 <= longitude <= 180
	 */
	private float longitude;
	
	/**
	 * Meters
	 */
	private float elevation;
	
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
