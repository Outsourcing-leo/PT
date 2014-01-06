package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rate extends IdEntity {
	private Double rate;
	private Long businessId;
	private Long weightBandId;
	private Long zoneGroupId;
	
	public Rate() {
	}

	public Rate(Long id) {
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