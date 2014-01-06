package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HighWeightBand extends IdEntity {
	private String name;
	private Double chargeableWeight;
	private Double begin;
	private Double end;
	private Integer addOnWeightInt;

	public HighWeightBand() {
	}

	public HighWeightBand(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getChargeableWeight() {
		return chargeableWeight;
	}

	public void setChargeableWeight(Double chargeableWeight) {
		this.chargeableWeight = chargeableWeight;
	}

	public Double getBegin() {
		return begin;
	}

	public void setBegin(Double begin) {
		this.begin = begin;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		this.end = end;
	}

	public Integer getAddOnWeightInt() {
		return addOnWeightInt;
	}

	public void setAddOnWeightInt(Integer addOnWeightInt) {
		this.addOnWeightInt = addOnWeightInt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}