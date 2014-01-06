package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ZoneGroup extends IdEntity {
	private String zoneType;
	private String zone;
	private String remark;

	public ZoneGroup() {
	}

	public ZoneGroup(Long id) {
		this.id = id;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
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