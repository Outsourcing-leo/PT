package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rev extends IdEntity {
	private Double rev;
	private Double kilo;
	private Double cons;
	private Long businessId;
	private Long weightBandId;
	private Long zoneGroupId;
	private Long countryId;
	private Long productId;
	private String payment;
	public Rev() {
	}
	
	

	public String getPayment() {
		return payment;
	}



	public void setPayment(String payment) {
		this.payment = payment;
	}



	public Rev(Long id) {
		this.id = id;
	}

	public Double getRev() {
		return rev;
	}

	public void setRev(Double rev) {
		this.rev = rev;
	}

	public Double getKilo() {
		return kilo;
	}

	public void setKilo(Double kilo) {
		this.kilo = kilo;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getWeightBandId() {
		return weightBandId;
	}

	public void setWeightBandId(Long weightBandId) {
		this.weightBandId = weightBandId;
	}

	public Long getZoneGroupId() {
		return zoneGroupId;
	}

	public void setZoneGroupId(Long zoneGroupId) {
		this.zoneGroupId = zoneGroupId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Double getCons() {
		return cons;
	}

	public void setCons(Double cons) {
		this.cons = cons;
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