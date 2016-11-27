/**
 * 
 */
package org.tephrochronology.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Used to populate the elemental data in {@link MMElement#elementData}.
 * 
 * @author Mark Royer
 *
 */
//@Entity
@Table(name="mm_elements_data")
public class MMElementData {
	
	@Id
	@JoinColumn(name="")
	protected MMElement mmElement;
	
	@Id
	protected Element element;
	
	protected Float value;
	
	protected Float std;
	
	protected Float me;
	
	protected String unit;

	/**
	 * @param value
	 * @param std
	 * @param me
	 * @param unit
	 */
	public MMElementData(Float value, Float std, Float me, String unit) {
		super();
		this.value = value;
		this.std = std;
		this.me = me;
		this.unit = unit;
	}
	
	
}