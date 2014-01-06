package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Customer extends IdEntity {
	private String account;
	private String cusName;
	private String channel;
	private String industry;
	private String serviceProvider;
	private String isFuleDeduction;
	private String fuelSurcharge;
	private String isReq;
	private String reqFuelSurcharge;
	private String payment;

	public Customer() {
	}

	public Customer(Long id) {
		this.id = id;
	}

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getIsFuleDeduction() {
		return isFuleDeduction;
	}

	public void setIsFuleDeduction(String isFuleDeduction) {
		this.isFuleDeduction = isFuleDeduction;
	}

	public String getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(String fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public String getIsReq() {
		return isReq;
	}

	public void setIsReq(String isReq) {
		this.isReq = isReq;
	}

	public String getReqFuelSurcharge() {
		return reqFuelSurcharge;
	}

	public void setReqFuelSurcharge(String reqFuelSurcharge) {
		this.reqFuelSurcharge = reqFuelSurcharge;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}