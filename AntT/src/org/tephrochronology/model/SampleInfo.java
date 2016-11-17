package org.tephrochronology.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Simple {@link Sample} results.
 * 
 * @author Mark Royer
 *
 */
@XmlRootElement(name="sample")
public class SampleInfo {

	private String sampleID;

	private String sampleType;

	private String longName;

	private String sampledBy;

	private LocalDate collectionDate;

	private String comments;

	private String siteID;

	private String iid;

	public SampleInfo() {
	}

	/**
	 * @param sampleID
	 * @param longName
	 * @param sampledBy
	 * @param collectionDate
	 * @param comments
	 * @param siteID
	 * @param iid
	 */
	public SampleInfo(Object[] objs) {
		super();
		// sample_type, sample_id, long_name, "
		// + "sampled_by, comments, collection_date, site_id, iid
		this.sampleType = (String) objs[0];
		this.sampleID = (String) objs[1];
		this.longName = (String) objs[2];
		this.sampledBy = (String) objs[3];
		this.comments = (String) objs[4];
		this.collectionDate = new LocalDateAttributeConverter()
				.convertToEntityAttribute((Date) objs[5]);
		this.siteID = (String) objs[6];
		this.iid = (String) objs[7];
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getSampledBy() {
		return sampledBy;
	}

	public void setSampledBy(String sampledBy) {
		this.sampledBy = sampledBy;
	}

	public LocalDate getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(LocalDate collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

}
