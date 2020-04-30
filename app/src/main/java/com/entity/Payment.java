package com.entity;

import java.io.Serializable;

public class Payment implements Serializable {
	private Integer pId;

	private Double pCost;

	private String pDate;

	private String pStatus;

	private Double pRealCost;

	private String oName;

	private static final long serialVersionUID = 1L;

	public Double getpRealCost() {
		return pRealCost;
	}

	public void setpRealCost(Double pRealCost) {
		this.pRealCost = pRealCost;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Double getpCost() {
		return pCost;
	}

	public void setpCost(Double pCost) {
		this.pCost = pCost;
	}

	public String getpDate() {
		return pDate;
	}

	public void setpDate(String pDate) {
		this.pDate = pDate;
	}

	public String getpStatus() {
		return pStatus;
	}

	public void setpStatus(String pStatus) {
		this.pStatus = pStatus == null ? null : pStatus.trim();
	}

	@Override
	public String toString() {
		return "{\"pId\":\"" + pId + "\", \"pCost\":\"" + pCost + "\", \"pDate\":\"" + pDate + "\", \"pStatus\":\""
				+ pStatus + "\", \"pRealCost\":\"" + pRealCost + "\", \"oName\":\"" + oName + "\"}";
	}

}