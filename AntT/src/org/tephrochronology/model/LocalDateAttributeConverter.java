/**
 * 
 */
package org.tephrochronology.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Mark Royer
 *
 */
@Converter(autoApply=true)
public class LocalDateAttributeConverter
		implements AttributeConverter<LocalDate, Date> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.
	 * Object)
	 */
	@Override
	public Date convertToDatabaseColumn(LocalDate locDate) {
		return (locDate == null ? null : Date.valueOf(locDate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.
	 * Object)
	 */
	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return (sqlDate == null ? null : sqlDate.toLocalDate());
	}

}
