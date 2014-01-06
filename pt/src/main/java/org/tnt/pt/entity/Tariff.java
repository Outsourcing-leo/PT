package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Tariff extends IdEntity {
	private Double tariff;
	private Long weightBandId;
	private Long zoneGroupId;
	
	public Tariff() {
	}

	public Tariff(Long id) {
		this.id = id;
	}

	public Double getTariff() {
		return tariff;
	}

	public void setTariff(Double tariff) {
		this.tariff = tariff;
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