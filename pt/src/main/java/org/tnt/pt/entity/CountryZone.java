package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryZone extends IdEntity {
	private Long countryId;
	private Long zoneGroupId;
	private Double ratio;

	public CountryZone() {
	}

	public CountryZone(Long id) {
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

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}