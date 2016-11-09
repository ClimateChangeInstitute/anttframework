/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mark Royer
 *
 */
public class IceCoreSample extends Sample {

	/**
	 * Not required
	 */
	private Integer volcanoNumber;

	private String drilledBy;

	private LocalDate drillingDate;

	private float coreDiameter;

	private float maxCoreDepth;

	private float coreAge;

	/**
	 * Years
	 */
	private String coreAgeRange;

	/**
	 * Meters
	 */
	private float topDepth;

	/**
	 * Meters
	 */
	private float bottomDepth;

	/**
	 * BP
	 */
	private float topYear;

	/**
	 * BP
	 */
	private float bottomYear;

	/**
	 * @param sampleID
	 * @param longName
	 * @param sampledBy
	 * @param collectionDate
	 * @param comments
	 * @param site
	 * @param instrument
	 * @param refs
	 * @param images
	 * @param volcanoNumber
	 * @param drilledBy
	 * @param drillingDate
	 * @param coreDiameter
	 * @param maxCoreDepth
	 * @param coreAge
	 * @param coreAgeRange
	 * @param topDepth
	 * @param bottomDepth
	 * @param topYear
	 * @param bottomYear
	 */
	public IceCoreSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Integer volcanoNumber, String drilledBy, LocalDate drillingDate,
			float coreDiameter, float maxCoreDepth, float coreAge,
			String coreAgeRange, float topDepth, float bottomDepth,
			float topYear, float bottomYear) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcanoNumber = volcanoNumber;
		this.drilledBy = drilledBy;
		this.drillingDate = drillingDate;
		this.coreDiameter = coreDiameter;
		this.maxCoreDepth = maxCoreDepth;
		this.coreAge = coreAge;
		this.coreAgeRange = coreAgeRange;
		this.topDepth = topDepth;
		this.bottomDepth = bottomDepth;
		this.topYear = topYear;
		this.bottomYear = bottomYear;
	}

	public Integer getVolcanoNumber() {
		return volcanoNumber;
	}

	public void setVolcanoNumber(Integer volcanoNumber) {
		this.volcanoNumber = volcanoNumber;
	}

	public String getDrilledBy() {
		return drilledBy;
	}

	public void setDrilledBy(String drilledBy) {
		this.drilledBy = drilledBy;
	}

	public LocalDate getDrillingDate() {
		return drillingDate;
	}

	public void setDrillingDate(LocalDate drillingDate) {
		this.drillingDate = drillingDate;
	}

	public float getCoreDiameter() {
		return coreDiameter;
	}

	public void setCoreDiameter(float coreDiameter) {
		this.coreDiameter = coreDiameter;
	}

	public float getMaxCoreDepth() {
		return maxCoreDepth;
	}

	public void setMaxCoreDepth(float maxCoreDepth) {
		this.maxCoreDepth = maxCoreDepth;
	}

	public float getCoreAge() {
		return coreAge;
	}

	public void setCoreAge(float coreAge) {
		this.coreAge = coreAge;
	}

	public String getCoreAgeRange() {
		return coreAgeRange;
	}

	public void setCoreAgeRange(String coreAgeRange) {
		this.coreAgeRange = coreAgeRange;
	}

	public float getTopDepth() {
		return topDepth;
	}

	public void setTopDepth(float topDepth) {
		this.topDepth = topDepth;
	}

	public float getBottomDepth() {
		return bottomDepth;
	}

	public void setBottomDepth(float bottomDepth) {
		this.bottomDepth = bottomDepth;
	}

	public float getTopYear() {
		return topYear;
	}

	public void setTopYear(float topYear) {
		this.topYear = topYear;
	}

	public float getBottomYear() {
		return bottomYear;
	}

	public void setBottomYear(float bottomYear) {
		this.bottomYear = bottomYear;
	}

}
