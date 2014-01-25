package org.tnt.pt.vo;

public class HWRateVO {

	private Double rate;
	private Long businessId;
	private Long weightBandId;
	private Long countryId;
	private Long productId;
	private String weightBandName;
	private String countryName;
	private String payment;
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
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
	public String getWeightBandName() {
		return weightBandName;
	}
	public void setWeightBandName(String weightBandName) {
		this.weightBandName = weightBandName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
}
