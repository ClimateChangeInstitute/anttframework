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
	@JoinColumn(name = "mm_element_id")
	@XmlInverseReference(mappedBy="elementData")
	protected MMElement mmElement;

	@Id
	@JoinColumn(name = "symbol")
	protected Chemistry symbol;

	@Id
	@JoinColumn(name = "unit")
	protected Unit unit;
	
	@Column(name = "val", nullable = false)
	protected Float value;

	@Column(name = "std")
	protected Float std;

	@Column(name = "me")
	protected Float me;
	
	@Column(name = "detection_limit")
	protected Float detectionLimit;

	/**
	 * @param mmElement
	 * @param element
	 * @param value
	 * @param std
	 * @param me
	 * @param unit
	 * @param detectionLimit
	 */
	public MMElementData(MMElement mmElement, Chemistry element, Float value,
			Float std, Float me, Unit unit, Float detectionLimit) {
		super();
		this.mmElement = mmElement;
		this.symbol = element;
		this.value = value;
		this.std = std;
		this.me = me;
		this.unit = unit;
		this.detectionLimit = detectionLimit;				
	}

	public MMElementData() {
	}

	public MMElement getMmElement() {
		return mmElement;
	}

	public void setMmElement(MMElement mmElement) {
		this.mmElement = mmElement;
	}

	public Chemistry getSymbol() {
		return symbol;
	}

	public void setSymbol(Chemistry symbol) {
		this.symbol = symbol;
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

	public Float getDetectionLimit() {
		return detectionLimit;
	}

	public void setDetectionLimit(Float detectionLimit) {
		this.detectionLimit = detectionLimit;
	}

}