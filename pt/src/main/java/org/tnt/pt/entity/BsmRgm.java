package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BsmRgm extends IdEntity {
	private Long depotId;
	private String userName;
	private String mailList;

	public BsmRgm() {
	}

	public BsmRgm(Long id) {
		this.id = id;
	}

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailList() {
		return mailList;
	}

	public void setMailList(String mailList) {
		this.mailList = mailList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}