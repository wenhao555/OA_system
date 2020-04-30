package com.entity;

import java.io.Serializable;

public class Advertise implements Serializable {
	private Integer aeId;

	private String aeContent;

	private static final long serialVersionUID = 1L;

	public Integer getAeId() {
		return aeId;
	}

	public void setAeId(Integer aeId) {
		this.aeId = aeId;
	}

	public String getAeContent() {
		return aeContent;
	}

	public void setAeContent(String aeContent) {
		this.aeContent = aeContent == null ? null : aeContent.trim();
	}

	@Override
	public String toString() {
		return "{\"aeId\":\"" + aeId + "\", \"aeContent\":\"" + aeContent + "\"}";
	}

}