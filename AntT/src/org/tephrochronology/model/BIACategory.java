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
@Table(name = "bia_categories")
@DiscriminatorValue(value = "B")
public class BIACategory extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "deep")
	private String deep;

	/**
	 * centimeters
	 */
	@Column(name = "thickness_cm")
	private float thickness;

	@Column(name = "trend")
	private String trend;

	public BIACategory() {

	}

	/**
	 * @param categoryID
	 * @param site
	 * @param deep
	 * @param thickness
	 * @param trend
	 */
	public BIACategory(String categoryID, Site site, String deep,
			float thickness, String trend) {
		super(categoryID, site);
		this.deep = deep;
		this.thickness = thickness;
		this.trend = trend;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
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
