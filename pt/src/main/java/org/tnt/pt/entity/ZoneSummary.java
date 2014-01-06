package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ZoneSummary extends IdEntity {
	private Long businessId;
	private Double consM;
	private Double consY;
	private Double  kiloM;
	private Double  kiloY;
	private Double revM;
	private Double revY;
	private Long zonegroupId;
	private String zoneType;
	private String payment;
	
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the zoneType
	 */
	public String getZoneType() {
		return zoneType;
	}

	/**
	 * @param zoneType the zoneType to set
	 */
	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	/**
	 * @return the consM
	 */
	public Double getConsM() {
		return consM;
	}

	/**
	 * @param consM the consM to set
	 */
	public void setConsM(Double consM) {
		this.consM = consM;
	}

	/**
	 * @return the consY
	 */
	public Double getConsY() {
		return consY;
	}

	/**
	 * @param consY the consY to set
	 */
	public void setConsY(Double consY) {
		this.consY = consY;
	}

	/**
	 * @return the kiloM
	 */
	public Double getKiloM() {
		return kiloM;
	}

	/**
	 * @param kiloM the kiloM to set
	 */
	public void setKiloM(Double kiloM) {
		this.kiloM = kiloM;
	}

	/**
	 * @return the kiloY
	 */
	public Double getKiloY() {
		return kiloY;
	}

	/**
	 * @param kiloY the kiloY to set
	 */
	public void setKiloY(Double kiloY) {
		this.kiloY = kiloY;
	}

	/**
	 * @return the revM
	 */
	public Double getRevM() {
		return revM;
	}

	/**
	 * @param revM the revM to set
	 */
	public void setRevM(Double revM) {
		this.revM = revM;
	}

	/**
	 * @return the revY
	 */
	public Double getRevY() {
		return revY;
	}

	/**
	 * @param revY the revY to set
	 */
	public void setRevY(Double revY) {
		this.revY = revY;
	}

	/**
	 * @return the zonegroupId
	 */
	public Long getZonegroupId() {
		return zonegroupId;
	}

	/**
	 * @param zonegroupId the zonegroupId to set
	 */
	public void setZonegroupId(Long zonegroupId) {
		this.zonegroupId = zonegroupId;
	}
	
	/**
	 * @return the businessId
	 */
	public Long getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}