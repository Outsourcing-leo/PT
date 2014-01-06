package org.tnt.pt.entity;

import java.util.Date;

public class Exam {

	private Long id;
	
	private Long businessId;
	
	private String examOppion;
	
	private String userId;
	
	private Date examTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getExamOppion() {
		return examOppion;
	}

	public void setExamOppion(String examOppion) {
		this.examOppion = examOppion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}
	
}
