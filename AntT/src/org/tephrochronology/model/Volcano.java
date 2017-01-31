/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "volcanoes")
//@formatter:off
@NamedQuery(name = Volcano.QUERY_VOLCANOES_BY_NUMBER, 
            query = "SELECT v FROM Volcano v ORDER BY v.volcanoNumber")
//@formatter:on
public class Volcano implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_VOLCANOES_BY_NUMBER = "org.tephrochronology.model.Volcano.OrderByVolcanoNumber";

	@Id
	@Column(name = "volcano_number")
	private int volcanoNumber;

	@Column(name = "old_volcano_number")
	private String oldVolcanoNumber;

	@Column(name = "volcano_name")
	private String name;

	@JoinColumn(name = "country", updatable = false, insertable = false)
	private Country country;

	@JoinColumn(name = "primary_volcano_type")
	private VolcanoType primaryVolcanoType;

	@Column(name = "last_known_eruption")
	private String lastKnownEruption;

	@JoinColumns({
			@JoinColumn(name = "region", referencedColumnName = "region"),
			@JoinColumn(name = "subregion", referencedColumnName = "subregion"),
			@JoinColumn(name = "country", referencedColumnName = "country") })
	private Region region;

	@JoinColumns({
			@JoinColumn(name = "subregion", referencedColumnName = "subregion", updatable = false, insertable = false),
			@JoinColumn(name = "country", referencedColumnName = "country", updatable = false, insertable = false) })
	private Subregion subregion;

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

	// @Column(name="dominant_rock_type")
	@JoinColumn(name = "dominant_rock_type")
	private RockType dominantRockType;

	@JoinColumn(name = "tectonic_setting")
	private TectonicType tectonicSetting;

	public Volcano() {
	}

	/**
	 * @param volcanoNumber
	 * @param oldVolcanoNumber
	 * @param name
	 * @param country
	 * @param primaryVolcanoType
	 * @param lastKnownEruption
	 * @param region
	 * @param subregion
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 * @param dominantRockType
	 * @param tectonicSetting
	 */
	public Volcano(int volcanoNumber, String oldVolcanoNumber, String name,
			Country country, VolcanoType primaryVolcanoType,
			String lastKnownEruption, Region region, Subregion subregion,
			float latitude, float longitude, float elevation,
			RockType dominantRockType, TectonicType tectonicSetting) {
		super();
		this.volcanoNumber = volcanoNumber;
		this.oldVolcanoNumber = oldVolcanoNumber;
		this.name = name;
		this.country = country;
		this.primaryVolcanoType = primaryVolcanoType;
		this.lastKnownEruption = lastKnownEruption;
		this.region = region;
		this.subregion = subregion;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.dominantRockType = dominantRockType;
		this.tectonicSetting = tectonicSetting;
	}

	public int getVolcanoNumber() {
		return volcanoNumber;
	}

	public void setVolcanoNumber(int volcanoNumber) {
		this.volcanoNumber = volcanoNumber;
	}

	public String getOldVolcanoNumber() {
		return oldVolcanoNumber;
	}

	public void setOldVolcanoNumber(String oldVolcanoNumber) {
		this.oldVolcanoNumber = oldVolcanoNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public VolcanoType getPrimaryVolcanoType() {
		return primaryVolcanoType;
	}

	public void setPrimaryVolcanoType(VolcanoType primaryVolcanoType) {
		this.primaryVolcanoType = primaryVolcanoType;
	}

	public String getLastKnownEruption() {
		return lastKnownEruption;
	}

	public void setLastKnownEruption(String lastKnownEruption) {
		this.lastKnownEruption = lastKnownEruption;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Subregion getSubregion() {
		return subregion;
	}

	public void setSubregion(Subregion subregion) {
		this.subregion = subregion;
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

	public RockType getDominantRockType() {
		return dominantRockType;
	}

	public void setDominantRockType(RockType dominantRockType) {
		this.dominantRockType = dominantRockType;
	}

	public TectonicType getTectonicSetting() {
		return tectonicSetting;
	}

	public void setTectonicSetting(TectonicType tectonicSetting) {
		this.tectonicSetting = tectonicSetting;
	}

}
