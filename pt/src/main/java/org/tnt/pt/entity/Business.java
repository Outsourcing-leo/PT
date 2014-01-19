package org.tnt.pt.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Business extends IdEntity {
	private Long customerId;
	private String reson;
	private Date applicationDate;
	private String depotCode;
	private String isNewCus;
	private String territory;
	private String isDocumentSender;
	private Integer consStop;
	private String description;
	private String weightRange;
	private String applicationReference;
	private String zoneType;
	private Double totalRev_R;
	private String state;
	private int suffix;//后缀
	private String isFollow;//假如payment为both isfollow的意思代表是否跟从
	private Date effectiveDate;//PT生效日期
	//customer 属性
	private String account;
	private String cusName;
	private String channel;
	private String top;//付款方式
	private String telPhone;
	private Double totalRev_S;
	private Double totalRev;
	public Business() {
	}

	public Business(Long id) {
		this.id = id;
	}


	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getIsNewCus() {
		return isNewCus;
	}

	public void setIsNewCus(String isNewCus) {
		this.isNewCus = isNewCus;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getIsDocumentSender() {
		return isDocumentSender;
	}

	public void setIsDocumentSender(String isDocumentSender) {
		this.isDocumentSender = isDocumentSender;
	}

	public Integer getConsStop() {
		return consStop;
	}

	public void setConsStop(Integer consStop) {
		this.consStop = consStop;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeightRange() {
		return weightRange;
	}

	public void setWeightRange(String weightRange) {
		this.weightRange = weightRange;
	}

	public String getApplicationReference() {
		return applicationReference;
	}

	public void setApplicationReference(String applicationReference) {
		this.applicationReference = applicationReference;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}
	
	

	public Double getTotalRev_R() {
		return totalRev_R;
	}

	public void setTotalRev_R(Double totalRev_R) {
		this.totalRev_R = totalRev_R;
	}

	public Double getTotalRev_S() {
		return totalRev_S;
	}

	public void setTotalRev_S(Double totalRev_S) {
		this.totalRev_S = totalRev_S;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the suffix
	 */
	public int getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(int suffix) {
		this.suffix = suffix;
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

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}
	
	public Double getTotalRev() {
		return totalRev;
	}

	public void setTotalRev(Double totalRev) {
		this.totalRev = totalRev;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}