package org.tephrochronology.model;
import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tephrochronology.model.AquaticCategory;
import org.tephrochronology.model.CorerType;
import org.tephrochronology.model.Site;

/**
 * 
 */

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "lake_categories")
@DiscriminatorValue(value = "L")
public class LakeCategory extends AquaticCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LakeCategory() {
	}

	/**
	 * @param categoryID
	 * @param site
	 * @param corerType
	 * @param age
	 * @param coreLength
	 * @param samplingDate
	 */
	public LakeCategory(String categoryID, Site site, CorerType corerType,
			String age, float coreLength, LocalDate samplingDate) {
		super(categoryID, site, corerType, age, coreLength, samplingDate);
	}

}
