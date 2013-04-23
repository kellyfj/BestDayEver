package com.nokia.scbe.bestdayever.model;

import java.util.Date;

public class Entry {
	Date start;
	Date end;
	String placeName;
	String placeUrl;
	
	public Entry() {}
	
	public Entry(Date start, Date end, String placeName, String placeUrl) {
		this.start = start;
		this.end = end;
		this.placeName = placeName;
		this.placeUrl = placeUrl;
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
	
	public String getPlaceName() {
		return placeName;
	}
	
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	public String getPlaceUrl() {
		return placeUrl;
	}
	
	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}
	
	@Override
	public String toString() {
		return "Entry [start=" + start + ", end=" + end + ", placeName="
				+ placeName + ", placeUrl=" + placeUrl + "]";
	}
	

}
