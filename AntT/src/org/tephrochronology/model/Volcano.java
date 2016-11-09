/**
 * 
 */
package org.tephrochronology.model;

/**
 * @author Mark Royer
 *
 */
public class Volcano {
	
	private int volcanoNumber;
	
	private String oldVolcanoNumber;
	
	private String name;
	
	private Country country;
	
	private VolcanoType primaryVolcanoType;
	
	private String lastKnownEruption;
	
	private Region region;
	
	private Subregion subregion;
	
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
	
	private RockType dominantRockType;
	
	private TectonicType tectonicSetting;

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
