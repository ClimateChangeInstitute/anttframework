/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents both lake and marine subtypes.
 * 
 * @author Mark Royer
 *
 */
public abstract class AquaticSample extends Sample {

	/**
	 * Not required
	 */
	private Integer volcanoNumber;

	private CoreType coreType;

	private String age;

	/**
	 * Meters
	 */
	private float coreLength;

	private LocalDate samplingDate;

	/**
	 * Meters
	 */
	private float depth;

	/**
	 * Meters
	 */
	private float top;

	/**
	 * Centimeters
	 */
	private float thickness;

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
	 * @param coreType
	 * @param age
	 * @param coreLength
	 * @param samplingDate
	 * @param depth
	 * @param top
	 * @param thickness
	 */
	public AquaticSample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Integer volcanoNumber, CoreType coreType, String age,
			float coreLength, LocalDate samplingDate, float depth, float top,
			float thickness) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcanoNumber = volcanoNumber;
		this.coreType = coreType;
		this.age = age;
		this.coreLength = coreLength;
		this.samplingDate = samplingDate;
		this.depth = depth;
		this.top = top;
		this.thickness = thickness;
	}

	public Integer getVolcanoNumber() {
		return volcanoNumber;
	}

	public void setVolcanoNumber(Integer volcanoNumber) {
		this.volcanoNumber = volcanoNumber;
	}

	public CoreType getCoreType() {
		return coreType;
	}

	public void setCoreType(CoreType coreType) {
		this.coreType = coreType;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public float getCoreLength() {
		return coreLength;
	}

	public void setCoreLength(float coreLength) {
		this.coreLength = coreLength;
	}

	public LocalDate getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(LocalDate samplingDate) {
		this.samplingDate = samplingDate;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

}
