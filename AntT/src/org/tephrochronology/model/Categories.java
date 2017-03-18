/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used for {@link CategoryInfo} XML results.
 * 
 * @author Mark Royer
 *
 */
@XmlRootElement(name = "categories")
public class Categories {

	/**
	 * 
	 */
	@XmlElement(name = "category")
	List<CategoryInfo> categories;

	public Categories() {
	}

	public Categories(List<CategoryInfo> categories) {
		super();
		this.categories = categories;
	}

	public List<CategoryInfo> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryInfo> categories) {
		this.categories = categories;
	}

}
