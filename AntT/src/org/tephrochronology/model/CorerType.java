/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name="corer_types")
public class CorerType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "corer_type")
	private String type;

	public CorerType(String type) {
		super();
		this.type = type;
	}

	public CorerType() {
		super();
		type = "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
