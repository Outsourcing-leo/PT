package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryGeo extends IdEntity {
	private Long countryId;
	private String geoZoneType;
	private Double ratio;

	public CountryGeo() {
	}

	public CountryGeo(Long id) {
		this.id = id;
	}


	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getGeoZoneType() {
		return geoZoneType;
	}

	public void setGeoZoneType(String geoZoneType) {
		this.geoZoneType = geoZoneType;
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