package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HWRate extends IdEntity {
	private Double rate;
	private Long businessId;
	private Long weightBandId;
	private Long countryId;
	private Long productId;
	private String payment;
	public HWRate() {
	}

	
	
	
	public String getPayment() {
		return payment;
	}




	public void setPayment(String payment) {
		this.payment = payment;
	}




	public HWRate(Long id) {
		this.id = id;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getWeightBandId() {
		return weightBandId;
	}

	public void setWeightBandId(Long weightBandId) {
		this.weightBandId = weightBandId;
	}


	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}