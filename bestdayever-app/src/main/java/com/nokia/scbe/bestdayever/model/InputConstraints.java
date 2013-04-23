package com.nokia.scbe.bestdayever.model;

import java.util.Date;

public class InputConstraints {
	Double latitute;
	Double longitude;
	Date start;
	Date end;
	String effort;
	String cost;
	String googleId;
	String noaId;
	
	public Double getLatitute() {
		return latitute;
	}
	
	public void setLatitute(Double latitute) {
		this.latitute = latitute;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public String getEffort() {
		return effort;
	}
	
	public void setEffort(String effort) {
		this.effort = effort;
	}
	public String getCost() {
		return cost;
	}
	
	public void setCost(String cost) {
		this.cost = cost;
	}
	
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
	public String getNoaId() {
		return noaId;
	}
	public void setNoaId(String noaId) {
		this.noaId = noaId;
	}
	
	public String getTimezone() {
		return "UTC-4:00";
	}
	
	@Override
	public String toString() {
		return "InputConstraints [latitute=" + latitute + ", longitude="
				+ longitude + ", start=" + start + ", end=" + end + ", effort="
				+ effort + ", cost=" + cost + ", googleId=" + googleId
				+ ", noaId=" + noaId + "]";
	}
	
	

}
