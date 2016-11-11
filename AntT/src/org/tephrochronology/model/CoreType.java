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
@Table(name="core_types")
public class CoreType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "core_type")
	private String type;

	public CoreType(String type) {
		super();
		this.type = type;
	}

	public CoreType() {
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
