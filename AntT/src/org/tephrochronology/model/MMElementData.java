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

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

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
	@XmlInverseReference(mappedBy="elementData")
	protected MMElement mmElement;

	@Id
	@JoinColumn(name = "element")
	protected Element element;

	@Id
	@JoinColumn(name = "unit")
	protected Unit unit;
	
	@Column(name = "val", nullable = false)
	protected Float value;

	@Column(name = "std", nullable = false)
	protected Float std;

	@Column(name = "me", nullable = false)
	protected Float me;

	/**
	 * @param value
	 * @param std
	 * @param me
	 * @param unit
	 */
	public MMElementData(MMElement mmElement, Element element, Float value,
			Float std, Float me, Unit unit) {
		super();
		this.mmElement = mmElement;
		this.element = element;
		this.value = value;
		this.std = std;
		this.me = me;
		this.unit = unit;
	}

	public MMElementData() {
	}

	public MMElement getMmElement() {
		return mmElement;
	}

	public void setMmElement(MMElement mmElement) {
		this.mmElement = mmElement;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Float getStd() {
		return std;
	}

	public void setStd(Float std) {
		this.std = std;
	}

	public Float getMe() {
		return me;
	}

	public void setMe(Float me) {
		this.me = me;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}