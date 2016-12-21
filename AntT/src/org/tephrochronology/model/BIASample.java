/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Blue Ice Area sample.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "bia_samples")
@DiscriminatorValue(value = "Blue Ice Area (BIA)")
public class BIASample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Not required
	 */
	@JoinColumn(name = "volcano_number", nullable = true)
	private Volcano volcano;

	@Column(name = "deep")
	private String deep;

	@Column(name = "sample_description")
	private String sampleDescription;

	@Column(name = "sample_media")
	private String sampleMedia;

	@Column(name = "unit_name")
	private String unitName;

	/**
	 * centimeters
	 */
	@Column(name = "thickness_cm")
	private float thickness;

	@Column(name = "trend")
	private String trend;

	public BIASample() {
	}

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
	 * @param volcano
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
			Volcano volcano, String deep, String sampleDescription,
			String sampleMedia, String unitName, float thickness,
			String trend) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcano = volcano;
		this.deep = deep;
		this.sampleDescription = sampleDescription;
		this.sampleMedia = sampleMedia;
		this.unitName = unitName;
		this.thickness = thickness;
		this.trend = trend;
	}

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
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
