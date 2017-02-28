/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "bia_categories")
@DiscriminatorValue(value = "B")
public class BIACategory extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "dip")
	private String dip;

	/**
	 * centimeters
	 */
	@Column(name = "thickness_cm")
	private String thickness;

	@Column(name = "trend")
	private String trend;

	public BIACategory() {

	}

	/**
	 * @param categoryID
	 * @param site
	 * @param dip
	 * @param thickness
	 * @param trend
	 * @param samples
	 */
	public BIACategory(String categoryID, Site site, String dip,
			String thickness, String trend, List<Sample> samples) {
		super(categoryID, site, samples);
		this.dip = dip;
		this.thickness = thickness;
		this.trend = trend;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}

	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

}
