package org.tnt.pt.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Product extends IdEntity {
	private String product;
	private Long productTypeId;
	private String name;
	private String remark;
	private String isavailable;
	private String isHighWeight;
	private Long highWeightId;

	public Product() {
	}

	public Product(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsavailable() {
		return isavailable;
	}

	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable;
	}

	public String getIsHighWeight() {
		return isHighWeight;
	}

	public void setIsHighWeight(String isHighWeight) {
		this.isHighWeight = isHighWeight;
	}

	public Long getHighWeightId() {
		return highWeightId;
	}

	public void setHighWeightId(Long highWeightId) {
		this.highWeightId = highWeightId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}