package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Discount extends IdEntity {
	private Double discount;
	private Long businessId;
	private Long weightBandId;
	private Long zoneGroupId;
	private String payment;
	
	
	public Discount() {
	}

	public Discount(Long id) {
		this.id = id;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}