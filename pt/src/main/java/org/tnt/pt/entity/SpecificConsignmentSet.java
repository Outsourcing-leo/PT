package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SpecificConsignmentSet extends IdEntity {
	private Long countryId;
	private Long zoneGroupId;
	private Long businessId;
	private Long productId;
	private Long weightBandId;
	private Integer consignment;
	private String payment;
	public SpecificConsignmentSet() {
	}

	public String getPayment() {
		return payment;
	}



	public void setPayment(String payment) {
		this.payment = payment;
	}

	public SpecificConsignmentSet(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getZoneGroupId() {
		return zoneGroupId;
	}

	public void setZoneGroupId(Long zoneGroupId) {
		this.zoneGroupId = zoneGroupId;
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

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * @return the weightBandId
	 */
	public Long getWeightBandId() {
		return weightBandId;
	}

	/**
	 * @param weightBandId the weightBandId to set
	 */
	public void setWeightBandId(Long weightBandId) {
		this.weightBandId = weightBandId;
	}

	/**
	 * @return the consignment
	 */
	public Integer getConsignment() {
		return consignment;
	}

	/**
	 * @param consignment the consignment to set
	 */
	public void setConsignment(Integer consignment) {
		this.consignment = consignment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}