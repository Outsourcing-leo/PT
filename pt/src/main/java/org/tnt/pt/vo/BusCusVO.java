package org.tnt.pt.vo;

import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;

public class BusCusVO {
	Business business = new Business();
	Customer customer = new Customer();
	String isFollow = "";
	String payment = "";
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	
}


