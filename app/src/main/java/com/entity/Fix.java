package com.entity;

import java.io.Serializable;

public class Fix implements Serializable {
	private Integer fId;

	private String fName;

	private String fStatus;

	private String oName;

	private String fDescription;

	private static final long serialVersionUID = 1L;

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName == null ? null : fName.trim();
	}

	public String getfStatus() {
		return fStatus;
	}

	public void setfStatus(String fStatus) {
		this.fStatus = fStatus == null ? null : fStatus.trim();
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName == null ? null : oName.trim();
	}

	public String getfDescription() {
		return fDescription;
	}

	public void setfDescription(String fDescription) {
		this.fDescription = fDescription == null ? null : fDescription.trim();
	}

	@Override
	public String toString() {
		return "{\"fId\":\"" + fId + "\", \"fName\":\"" + fName + "\", \"fStatus\":\"" + fStatus + "\", \"oName\":\""
				+ oName + "\", \"fDescription\":\"" + fDescription + "\"}";
	}

}