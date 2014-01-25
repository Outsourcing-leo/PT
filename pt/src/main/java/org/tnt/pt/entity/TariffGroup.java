package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TariffGroup extends IdEntity {
	private Double weight;
	private Long weightBandId;
	private Long productId;
	private String type;
	
	public TariffGroup() {
	}


	public Double getWeight() {
		return weight;
	}



	public void setWeight(Double weight) {
		this.weight = weight;
	}



	public Long getWeightBandId() {
		return weightBandId;
	}



	public void setWeightBandId(Long weightBandId) {
		this.weightBandId = weightBandId;
	}



	public Long getProductId() {
		return productId;
	}



	public void setProductId(Long productId) {
		this.productId = productId;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}