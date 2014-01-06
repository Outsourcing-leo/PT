package org.tnt.pt.dmsentity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FSI{
	private String is_fsi;
	private Double fs_rate;
	private Double fsi_dis_rate;

	public FSI() {
	}

	public String getIs_fsi() {
		return is_fsi;
	}


	public void setIs_fsi(String is_fsi) {
		this.is_fsi = is_fsi;
	}


	public Double getFs_rate() {
		return fs_rate;
	}


	public void setFs_rate(Double fs_rate) {
		this.fs_rate = fs_rate;
	}


	public Double getFsi_dis_rate() {
		return fsi_dis_rate;
	}


	public void setFsi_dis_rate(Double fsi_dis_rate) {
		this.fsi_dis_rate = fsi_dis_rate;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}