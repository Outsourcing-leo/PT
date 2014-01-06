package org.tnt.pt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RevVO {
	private Double rev =0.00;
	private Double kilo  =0.00;
	private Double cons  =0.00;
	private Double rpc  =0.00;
	private Double rpk  =0.00;
	private Double wpc  =0.00;
	private Double gm  =0.00;
	private Long businessId;
	private Long weightBandId;
	private Long zoneGroupId;
	private Long countryId;
	private String zone;
	private String COUNTRYNAME;
	private String WEIGHTNAME;
	private String PRODUCTNAME;
	private String multichoised;
	private String chargeableWeight;
	private Double dm  =0.00;
	private Double fm  =0.00;
	
	public RevVO() {
	}


	public Double getRev() {
		return rev;
	}

	public void setRev(Double rev) {
		this.rev = rev;
	}

	public Double getKilo() {
		return kilo;
	}

	public void setKilo(Double kilo) {
		this.kilo = kilo;
	}

	
	public Double getRpc() {
		return rpc;
	}


	public void setRpc(Double rpc) {
		this.rpc = rpc;
	}


	public Double getRpk() {
		return rpk;
	}


	public void setRpk(Double rpk) {
		this.rpk = rpk;
	}


	public Double getWpc() {
		return wpc;
	}


	public void setWpc(Double wpc) {
		this.wpc = wpc;
	}


	public Double getGm() {
		return gm;
	}


	public void setGm(Double gm) {
		this.gm = gm;
	}


	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getWeightBandId() {
		return weightBandId;
	}

	public void setWeightBandId(Long weightBandId) {
		this.weightBandId = weightBandId;
	}

	public Long getZoneGroupId() {
		return zoneGroupId;
	}

	public void setZoneGroupId(Long zoneGroupId) {
		this.zoneGroupId = zoneGroupId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Double getCons() {
		return cons;
	}

	public void setCons(Double cons) {
		this.cons = cons;
	}

	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public String getCOUNTRYNAME() {
		return COUNTRYNAME;
	}


	public void setCOUNTRYNAME(String cOUNTRYNAME) {
		COUNTRYNAME = cOUNTRYNAME;
	}


	public String getWEIGHTNAME() {
		return WEIGHTNAME;
	}


	public void setWEIGHTNAME(String wEIGHTNAME) {
		WEIGHTNAME = wEIGHTNAME;
	}


	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}


	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}


	public String getMultichoised() {
		return multichoised;
	}


	public void setMultichoised(String multichoised) {
		this.multichoised = multichoised;
	}


	public String getChargeableWeight() {
		return chargeableWeight;
	}


	public void setChargeableWeight(String chargeableWeight) {
		this.chargeableWeight = chargeableWeight;
	}


	public Double getDm() {
		return dm;
	}


	public void setDm(Double dM) {
		dm = dM;
	}


	public Double getFm() {
		return fm;
	}


	public void setFm(Double fM) {
		fm = fM;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}