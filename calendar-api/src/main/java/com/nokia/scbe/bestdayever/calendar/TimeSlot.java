package com.nokia.scbe.bestdayever.calendar;

import java.util.Date;

public class TimeSlot {
	private Date start;
	private Date end;
	
	public TimeSlot(Date start, Date end) {
		this.start = start;
		this.end = end;
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

	@Override
	public String toString() {
		return "TimeSlot [start: " + start + ", end: " + end + "]";
	}
}
