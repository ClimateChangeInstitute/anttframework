/**
 * 
 */
package org.tephrochronology.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Used to populate the elemental data in {@link MMElement#elementData}.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "mm_elements_data")
@IdClass(MMElementDataID.class)
public class MMElementData {

	@Id
	@JoinColumn(name = "longsample_id")
	protected MMElement mmElement;

	@Id
	@JoinColumn(name = "element")
	protected Element element;

	@Column(name = "val")
	protected Float value;

	@Column(name = "std")
	protected Float std;

	@Column(name = "me")
	protected Float me;

	@Column(name = "unit")
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

	public MMElementData() {
	}

}