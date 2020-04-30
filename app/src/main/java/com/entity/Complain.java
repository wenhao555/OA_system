package com.entity;

import java.io.Serializable;

public class Complain implements Serializable {
	private Integer cId;

	private String oName;

	private String cContent;

	private String cFeedback;

	private static final long serialVersionUID = 1L;

	public String getcContent() {
		return cContent;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public String getcFeedback() {
		return cFeedback;
	}

	public void setcFeedback(String cFeedback) {
		this.cFeedback = cFeedback;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName == null ? null : oName.trim();
	}

	@Override
	public String toString() {
		return "{\"cId\":\"" + cId + "\", \"oName\":\"" + oName + "\", \"cContent\":\"" + cContent
				+ "\", \"cFeedback\":\"" + cFeedback + "\"}";
	}

}