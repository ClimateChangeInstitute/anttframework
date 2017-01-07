/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * @author Mark Royer
 *
 */
@MappedSuperclass
public class AquaticCategory extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "corer_type")
	private CorerType corerType;

	@Column(name = "age")
	private String age;

	/**
	 * Meters
	 */
	@Column(name = "core_length_m")
	private float coreLength;

	@Column(name = "collection_date")
	private LocalDate collectionDate;
	
	public AquaticCategory() {
		super();
	}

	public AquaticCategory(String categoryID, Site site, CorerType corerType,
			String age, float coreLength, LocalDate collectionDate) {
		super(categoryID, site);
		this.corerType = corerType;
		this.age = age;
		this.coreLength = coreLength;
		this.collectionDate = collectionDate;
	}

	public CorerType getCorerType() {
		return corerType;
	}

	public void setCorerType(CorerType corerType) {
		this.corerType = corerType;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public float getCoreLength() {
		return coreLength;
	}

	public void setCoreLength(float coreLength) {
		this.coreLength = coreLength;
	}

	public LocalDate getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(LocalDate collectionDate) {
		this.collectionDate = collectionDate;
	}

}
