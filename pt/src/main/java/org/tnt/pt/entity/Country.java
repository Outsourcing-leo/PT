package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Country extends IdEntity {
	private String countryCode;
	private String countryNo;
	private String countryName;
	private String  depotCode;
	private String isAvailable;

	public Country() {
	}

	public Country(Long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the depotCode
	 */
	public String getDepotCode() {
		return depotCode;
	}

	/**
	 * @param depotCode the depotCode to set
	 */
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}