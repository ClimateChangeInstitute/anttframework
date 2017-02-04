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
	private String thickness;

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
			String thickness, String trend) {
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
