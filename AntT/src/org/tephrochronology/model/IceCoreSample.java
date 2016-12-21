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
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "icecore_samples")
@DiscriminatorValue(value = "I")
public class IceCoreSample extends Sample {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Not required
	 */
	@JoinColumn(name = "volcano_number", nullable = true)
	private Volcano volcano;

	@Column(name = "drilled_by")
	private String drilledBy;

	@Column(name = "drilling_date")
	private LocalDate drillingDate;

	@Column(name = "core_diameter")
	private float coreDiameter;

	@Column(name = "max_core_depth")
	private float maxCoreDepth;

	@Column(name = "core_age")
	private float coreAge;

	/**
	 * Years
	 */
	@Column(name = "core_age_range")
	private String coreAgeRange;

	/**
	 * Meters
	 */
	@Column(name = "topdepth_m")
	private float topDepth;

	/**
	 * Meters
	 */
	@Column(name = "bottomdepth_m")
	private float bottomDepth;

	/**
	 * BP
	 */
	@Column(name = "topyear_bp")
	private float topYear;

	/**
	 * BP
	 */
	@Column(name = "bottomyear_bp")
	private float bottomYear;

	public IceCoreSample() {
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
			Volcano volcano, String drilledBy, LocalDate drillingDate,
			float coreDiameter, float maxCoreDepth, float coreAge,
			String coreAgeRange, float topDepth, float bottomDepth,
			float topYear, float bottomYear) {
		super(sampleID, longName, sampledBy, collectionDate, comments, site,
				instrument, refs, images);
		this.volcano = volcano;
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

	public Volcano getVolcano() {
		return volcano;
	}

	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
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
