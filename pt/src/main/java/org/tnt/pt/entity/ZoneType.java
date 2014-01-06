package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ZoneType extends IdEntity {
	private String zoneType;
	private Long document;
	private Long nonDocument;
	private Long economy;
	private String isAvailable;
	private String remark;

	public ZoneType() {
	}

	public ZoneType(Long id) {
		this.id = id;
	}


	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public Long getNonDocument() {
		return nonDocument;
	}

	public void setNonDocument(Long nonDocument) {
		this.nonDocument = nonDocument;
	}

	public Long getEconomy() {
		return economy;
	}

	public void setEconomy(Long economy) {
		this.economy = economy;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}