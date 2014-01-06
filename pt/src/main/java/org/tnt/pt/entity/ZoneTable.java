package org.tnt.pt.entity;



public class ZoneTable  extends IdEntity {

	private String zone;
	
	private String countryCode;
	
	private String countryName;
	
	private String depot;
	
	private String availabilty;
	
	private String defaultCountry;

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getAvailabilty() {
		return availabilty;
	}

	public void setAvailabilty(String availabilty) {
		this.availabilty = availabilty;
	}

	public String getDefaultCountry() {
		return defaultCountry;
	}

	public void setDefaultCountry(String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}
	
}
