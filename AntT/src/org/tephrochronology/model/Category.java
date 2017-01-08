/**
 * 
 */
package org.tephrochronology.model;

import static javax.persistence.DiscriminatorType.CHAR;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Represents a collection of samples, for example, an ice core.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "categories")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "category_type", discriminatorType = CHAR)
@XmlSeeAlso({ BIACategory.class, IceCoreCategory.class, LakeCategory.class,
		MarineCategory.class, OutcropCategory.class })
public abstract class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "category_id")
	private String categoryID;

	@JoinColumn(name = "site_id")
	private Site site;

	public Category() {
	}

	/**
	 * @param categoryID
	 * @param site
	 */
	public Category(String categoryID, Site site) {
		super();
		this.categoryID = categoryID;
		this.site = site;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
