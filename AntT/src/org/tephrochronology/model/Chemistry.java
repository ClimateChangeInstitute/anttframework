/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "chemistries")
//@formatter:off
@NamedQuery(name = Chemistry.QUERY_CHEMISTRIES_BY_MASS_DESC_AND_NAME, 
			query = "SELECT e "
				  + "FROM Chemistry e "
				  + "ORDER BY e.molecularMass DESC, e.name")
//@formatter:on
public class Chemistry implements Serializable, Comparable<Chemistry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_CHEMISTRIES_BY_MASS_DESC_AND_NAME = "org.tephrochronology.model.Chemistry.OrderByMolecularMassDescAndName";

	@Id
	@Column(name = "symbol")
	protected String symbol;

	@Column(name = "name")
	protected String name;

	@Column(name = "format")
	protected String format;

	@Column(name = "molecular_mass")
	protected Float molecularMass;

	/**
	 * Only for elements
	 */
	@Column(name = "atomic_number")
	protected Integer atomicNumber;
	
	@Column(name = "preferred_order")
	protected int preferredOrder;

	/**
	 * @param symbol
	 * @param name
	 * @param format
	 * @param molecularMass
	 * @param atomicNumber
	 * @param preferredOrder
	 */
	public Chemistry(String symbol, String name, String format,
			Float molecularMass, Integer atomicNumber, int preferredOrder) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.format = format;
		this.molecularMass = molecularMass;
		this.atomicNumber = atomicNumber;
		this.preferredOrder = preferredOrder;
	}

	public Chemistry() {
		super();
	}

	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}

	@Override
	public int compareTo(Chemistry o) {
		return symbol.compareTo(o.symbol);
	}

	@Override
	public int hashCode() {
		return symbol.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Chemistry) {
			Chemistry other = (Chemistry) obj;
			return symbol.equals(other.symbol);
		} else
			return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Float getMolecularMass() {
		return molecularMass;
	}

	public void setMolecularMass(Float molecularMass) {
		this.molecularMass = molecularMass;
	}

	public Integer getAtomicNumber() {
		return atomicNumber;
	}

	public void setAtomicNumber(Integer atomicNumber) {
		this.atomicNumber = atomicNumber;
	}

	public int getPreferredOrder() {
		return preferredOrder;
	}

	public void setPreferredOrder(int preferredOrder) {
		this.preferredOrder = preferredOrder;
	}

}