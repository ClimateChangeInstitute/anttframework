/**
 * 
 */
package org.tephrochronology.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * {@link Category} results that only include a small amount of sample
 * information.
 * 
 * @author Mark Royer
 *
 */
public class CategoryInfo {

	private String categoryType;

	private String categoryID;
	
	private String siteID;

	@XmlElement(name="sample")	
	private List<SampleInfo> samples;

	public CategoryInfo() {
	}

	public CategoryInfo(String categoryType, Category c) {
		super();
		this.categoryType = categoryType;
		this.categoryID = c.getCategoryID();
		this.siteID = c.getSite().getSiteID();

		samples = new ArrayList<>();

		for (Sample s : c.getSamples()) {
			samples.add(new SampleInfo(categoryType, s.getSampleID(),
					s.getSecondaryID(), s.getSampledBy(), s.getComments(),
					s.getCollectionDate(), c.getCategoryID(),
					c.getSite().getLatitude(), c.getSite().getLongitude()));
		}
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setSampleType(String sampleType) {
		this.categoryType = sampleType;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public List<SampleInfo> getSamples() {
		return samples;
	}

	public void setSamples(List<SampleInfo> samples) {
		this.samples = samples;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

}
