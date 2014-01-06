package org.tnt.pt.entity;

public class Review extends IdEntity {

	/*<th style="text-align:center;">Analysis Fields you selected (multichoised)</th>
	<th style="text-align:center;">Rev</th>
	<th style="text-align:center;">Cons</th>
	<th style="text-align:center;">Kilos</th>
	<th style="text-align:center;">RPC</th>
	<th style="text-align:center;">RPK</th>
	<th style="text-align:center;">WPC</th>
	<th style="text-align:center;">GM(%)</th>*/
	
	private String multichoised;
	
	private double rev;
	
	private double cons;
	
	private double kilos;
	
	private double rpc;
	
	private double rpk;
	
	private double wpc;
	
	private double gm;

	public String getMultichoised() {
		return multichoised;
	}

	public void setMultichoised(String multichoised) {
		this.multichoised = multichoised;
	}

	public double getRev() {
		return rev;
	}

	public void setRev(double rev) {
		this.rev = rev;
	}

	public double getCons() {
		return cons;
	}

	public void setCons(double cons) {
		this.cons = cons;
	}

	public double getKilos() {
		return kilos;
	}

	public void setKilos(double kilos) {
		this.kilos = kilos;
	}

	public double getRpc() {
		return rpc;
	}

	public void setRpc(double rpc) {
		this.rpc = rpc;
	}

	public double getRpk() {
		return rpk;
	}

	public void setRpk(double rpk) {
		this.rpk = rpk;
	}

	public double getWpc() {
		return wpc;
	}

	public void setWpc(double wpc) {
		this.wpc = wpc;
	}

	public double getGm() {
		return gm;
	}

	public void setGm(double gm) {
		this.gm = gm;
	}
	
}
