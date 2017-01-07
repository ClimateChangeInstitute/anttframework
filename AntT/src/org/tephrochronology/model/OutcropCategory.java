/**
 * 
 */
package org.tephrochronology.model;

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
	 */
	public OutcropCategory(String categoryID, Site site) {
		super(categoryID, site);
	}

}
