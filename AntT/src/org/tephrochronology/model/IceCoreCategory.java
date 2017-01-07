/**
 * 
 */
package org.tephrochronology.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "icecore_categories")
@DiscriminatorValue(value = "I")
public class IceCoreCategory extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "drilled_by")
	private String drilledBy;

	@Column(name = "drilling_dates")
	private String drillingDates; // Possibly LocalDate in future?

	@Column(name = "core_diameter")
	private String coreDiameter;

	@Column(name = "max_core_depth")
	private String maxCoreDepth;

	/**
	 * Years type unknown
	 */
	@Column(name = "core_age_range")
	private String coreAgeRange;

	public IceCoreCategory() {

	}

	/**
	 * @param categoryID
	 * @param site
	 * @param drilledBy
	 * @param drillingDates
	 * @param coreDiameter
	 * @param maxCoreDepth
	 * @param coreAgeRange
	 */
	public IceCoreCategory(String categoryID, Site site, String drilledBy,
			String drillingDates, String coreDiameter, String maxCoreDepth,
			String coreAgeRange) {
		super(categoryID, site);
		this.drilledBy = drilledBy;
		this.drillingDates = drillingDates;
		this.coreDiameter = coreDiameter;
		this.maxCoreDepth = maxCoreDepth;
		this.coreAgeRange = coreAgeRange;
	}

	public String getDrilledBy() {
		return drilledBy;
	}

	public void setDrilledBy(String drilledBy) {
		this.drilledBy = drilledBy;
	}

	public String getDrillingDates() {
		return drillingDates;
	}

	public void setDrillingDates(String drillingDates) {
		this.drillingDates = drillingDates;
	}

	public String getCoreDiameter() {
		return coreDiameter;
	}

	public void setCoreDiameter(String coreDiameter) {
		this.coreDiameter = coreDiameter;
	}

	public String getMaxCoreDepth() {
		return maxCoreDepth;
	}

	public void setMaxCoreDepth(String maxCoreDepth) {
		this.maxCoreDepth = maxCoreDepth;
	}

	public String getCoreAgeRange() {
		return coreAgeRange;
	}

	public void setCoreAgeRange(String coreAgeRange) {
		this.coreAgeRange = coreAgeRange;
	}
}
