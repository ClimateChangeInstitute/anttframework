/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;

/**
 * Used for shallow {@link MMElement} query results.
 * 
 * @author Mark Royer
 *
 */
public class MMElementInfo {

	private String mmElementID;

	private String sampleID;

	private String comments;

	private MethodType methodType;

	private String iid;

	private String dateMeasured;

	private String measuredBy;

	private int numberOfMeasurements;

	private Float originalTotal;

	private Float calculatedTotal;

	private String instrumentSettings;

	private Float h2o_plus;

	private Float h2o_minus;

	private Float loi;

	private List<MMElementData> elementData;

	/**
	 * sample_type from database: B, I, L, M, or O.
	 * 
	 * @see Sample
	 */
	private String sampleType;

	public MMElementInfo() {
	}

	public MMElementInfo(String mmElementID, String sampleID, String comments,
			MethodType methodType, String iid, String dateMeasured,
			String measuredBy, int numberOfMeasurements, Float originalTotal,
			Float calculatedTotal, String instrumentSettings,
			List<MMElementData> elementData, String sampleType) {
		super();
		this.mmElementID = mmElementID;
		this.sampleID = sampleID;
		this.comments = comments;
		this.methodType = methodType;
		this.iid = iid;
		this.dateMeasured = dateMeasured;
		this.measuredBy = measuredBy;
		this.numberOfMeasurements = numberOfMeasurements;
		this.originalTotal = originalTotal;
		this.calculatedTotal = calculatedTotal;
		this.instrumentSettings = instrumentSettings;
		this.elementData = elementData;
		this.sampleType = sampleType;
	}

	public MMElementInfo(MMElement e) {
		this(e.getMmElementID(), e.getSample().getSampleID(), e.getComments(),
				e.getMethodType(),
				e.getInstrument() == null ? null : e.getInstrument().getId(),
				e.getDateMeasured(), e.getMeasuredBy(),
				e.getNumberOfMeasurements(), e.getOriginalTotal(),
				e.getCalculatedTotal(), e.getInstrumentSettings(),
				e.getElementData(), e.getSample().getClass()
						.getAnnotation(DiscriminatorValue.class).value());
	}

	public String getMmElementID() {
		return mmElementID;
	}

	public void setMmElementID(String mmElementID) {
		this.mmElementID = mmElementID;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public MethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getDateMeasured() {
		return dateMeasured;
	}

	public void setDateMeasured(String dateMeasured) {
		this.dateMeasured = dateMeasured;
	}

	public String getMeasuredBy() {
		return measuredBy;
	}

	public void setMeasuredBy(String measuredBy) {
		this.measuredBy = measuredBy;
	}

	public int getNumberOfMeasurements() {
		return numberOfMeasurements;
	}

	public void setNumberOfMeasurements(int numberOfMeasurements) {
		this.numberOfMeasurements = numberOfMeasurements;
	}

	public Float getOriginalTotal() {
		return originalTotal;
	}

	public void setOriginalTotal(Float originalTotal) {
		this.originalTotal = originalTotal;
	}

	public Float getCalculatedTotal() {
		return calculatedTotal;
	}

	public void setCalculatedTotal(Float calculatedTotal) {
		this.calculatedTotal = calculatedTotal;
	}

	public String getInstrumentSettings() {
		return instrumentSettings;
	}

	public void setInstrumentSettings(String instrumentSettings) {
		this.instrumentSettings = instrumentSettings;
	}

	public Float getH2o_plus() {
		return h2o_plus;
	}

	public void setH2o_plus(Float h2o_plus) {
		this.h2o_plus = h2o_plus;
	}

	public Float getH2o_minus() {
		return h2o_minus;
	}

	public void setH2o_minus(Float h2o_minus) {
		this.h2o_minus = h2o_minus;
	}

	public Float getLoi() {
		return loi;
	}

	public void setLoi(Float loi) {
		this.loi = loi;
	}

	public List<MMElementData> getElementData() {
		return elementData;
	}

	public void setElementData(List<MMElementData> elementData) {
		this.elementData = elementData;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

}
