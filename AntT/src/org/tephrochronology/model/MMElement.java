/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Mark Royer
 *
 */
public class MMElement {

	/**
	 * Used to populate the elemental data in {@link MMElement#elementData}.
	 * 
	 * @author Mark Royer
	 *
	 */
	protected class MMElementData {
		protected Float value;
		protected Float std;
		protected Float me;
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
	};

	private String longsampleID;

	private Sample sample;

	private String comments;

	private MethodType methodType;

	private Instrument instrument;

	private LocalDate dateMeasured;

	private String measuredBy;

	private int numberOfMeasurements;

	private float originalTotal;

	private float calculatedTotal;

	private String instrumentSettings;

	private Float h2o_plus;

	private Float h2o_minus;

	private Float loi;

	private Map<String, MMElementData> elementData;

	/**
	 * @param longsampleID
	 * @param sample
	 * @param comments
	 * @param methodType
	 * @param instrument
	 * @param dateMeasured
	 * @param measuredBy
	 * @param numberOfMeasurements
	 * @param originalTotal
	 * @param calculatedTotal
	 * @param instrumentSettings
	 * @param h2o_plus
	 * @param h2o_minus
	 * @param loi
	 * @param elementData
	 */
	public MMElement(String longsampleID, Sample sample, String comments,
			MethodType methodType, Instrument instrument,
			LocalDate dateMeasured, String measuredBy, int numberOfMeasurements,
			float originalTotal, float calculatedTotal,
			String instrumentSettings, Float h2o_plus, Float h2o_minus,
			Float loi, Map<String, MMElementData> elementData) {
		super();
		this.longsampleID = longsampleID;
		this.sample = sample;
		this.comments = comments;
		this.methodType = methodType;
		this.instrument = instrument;
		this.dateMeasured = dateMeasured;
		this.measuredBy = measuredBy;
		this.numberOfMeasurements = numberOfMeasurements;
		this.originalTotal = originalTotal;
		this.calculatedTotal = calculatedTotal;
		this.instrumentSettings = instrumentSettings;
		this.h2o_plus = h2o_plus;
		this.h2o_minus = h2o_minus;
		this.loi = loi;
		this.elementData = elementData;
	}

	public String getLongsampleID() {
		return longsampleID;
	}

	public void setLongsampleID(String longsampleID) {
		this.longsampleID = longsampleID;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
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

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public LocalDate getDateMeasured() {
		return dateMeasured;
	}

	public void setDateMeasured(LocalDate dateMeasured) {
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

	public float getOriginalTotal() {
		return originalTotal;
	}

	public void setOriginalTotal(float originalTotal) {
		this.originalTotal = originalTotal;
	}

	public float getCalculatedTotal() {
		return calculatedTotal;
	}

	public void setCalculatedTotal(float calculatedTotal) {
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

	public Map<String, MMElementData> getElementData() {
		return elementData;
	}

	public void setElementData(Map<String, MMElementData> elementData) {
		this.elementData = elementData;
	}

}
