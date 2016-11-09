/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Blue Ice Area sample.
 * 
 * @author Mark Royer
 *
 */
public class BIASample extends Sample {

	/**
	 * Not required
	 */
	private Integer volcanoNumber;

	private String deep;

	private String sampleDescription;

	private String sampleMedia;

	private String unitName;

	/**
	 * centimeters
	 */
	private float thickness;

	private String trend;

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
	 * @param deep
	 * @param sampleDescription
	 * @param sampleMedia
	 * @param unitName
	 * @param thickness
	 * @param trend
	 */
	public BIASample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument, List<Ref> refs, List<Image> images,
			Integer volcanoNumber, String deep, String sampleDescription,
			String sampleMedia, String unitName, float thickness,
			String trend) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcanoNumber = volcanoNumber;
		this.deep = deep;
		this.sampleDescription = sampleDescription;
		this.sampleMedia = sampleMedia;
		this.unitName = unitName;
		this.thickness = thickness;
		this.trend = trend;
	}

	public Integer getVolcanoNumber() {
		return volcanoNumber;
	}

	public void setVolcanoNumber(Integer volcanoNumber) {
		this.volcanoNumber = volcanoNumber;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getSampleDescription() {
		return sampleDescription;
	}

	public void setSampleDescription(String sampleDescription) {
		this.sampleDescription = sampleDescription;
	}

	public String getSampleMedia() {
		return sampleMedia;
	}

	public void setSampleMedia(String sampleMedia) {
		this.sampleMedia = sampleMedia;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

}
