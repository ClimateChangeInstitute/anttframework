/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "outcrop_categories")
@DiscriminatorValue(value = "O")
public class OutcropCategory extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public OutcropCategory() {
	}

	/**
	 * @param categoryID
	 * @param site
	 * @param samples
	 */
	public OutcropCategory(String categoryID, Site site, List<Sample> samples) {
		super(categoryID, site, samples);
	}

}
