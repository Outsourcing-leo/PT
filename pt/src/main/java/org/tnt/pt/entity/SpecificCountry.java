package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SpecificCountry extends IdEntity {
	private Long countryId;
	private Long zoneGroupId;
	private Long businessId;
	private Long productId;
	private String payment;
	public SpecificCountry() {
	}

	
	public String getPayment() {
		return payment;
	}



	public void setPayment(String payment) {
		this.payment = payment;
	}

	public SpecificCountry(Long id) {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}