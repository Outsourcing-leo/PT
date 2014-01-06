package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WeightBand extends IdEntity {
	private Long productId;
	private String name;
	private Double chargeableWeight;
	private Double begin;
	private Double end;
	private Integer addOnWeightInt;
	private String  type;
	private String isHighWeight;
	private Long weightbandGroupId;
	private String weightbandGroup;

	public WeightBand() {
	}

	public WeightBand(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getIsHighWeight() {
		return isHighWeight;
	}

	public void setIsHighWeight(String isHighWeight) {
		this.isHighWeight = isHighWeight;
	}
	

	public Long getWeightbandGroupId() {
		return weightbandGroupId;
	}

	public void setWeightbandGroupId(Long weightbandGroupId) {
		this.weightbandGroupId = weightbandGroupId;
	}

	
	public String getWeightbandGroup() {
		return weightbandGroup;
	}

	public void setWeightbandGroup(String weightbandGroup) {
		this.weightbandGroup = weightbandGroup;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}