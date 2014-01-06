package org.tnt.pt.entity;

import javax.persistence.Entity;

@Entity
public class PTSLoad  extends IdEntity {

	private String depotcode;
	
	private int newsNum;
	
	private int totalNum;

	public String getDepotcode() {
		return depotcode;
	}

	public void setDepotcode(String depotcode) {
		this.depotcode = depotcode;
	}

	public int getNewsNum() {
		return newsNum;
	}

	public void setNewsNum(int newsNum) {
		this.newsNum = newsNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
}
