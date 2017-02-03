package org.tephrochronology.model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "marine_categories")
@DiscriminatorValue(value = "M")
public class MarineCategory extends AquaticCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MarineCategory() {
	}

	/**
	 * @param categoryID
	 * @param site
	 * @param corerType
	 * @param age
	 * @param coreLength
	 * @param collectionDate
	 */
	public MarineCategory(String categoryID, Site site, CorerType corerType,
			String age, Float coreLength, String collectionDate) {
		super(categoryID, site, corerType, age, coreLength, collectionDate);
	}

}
