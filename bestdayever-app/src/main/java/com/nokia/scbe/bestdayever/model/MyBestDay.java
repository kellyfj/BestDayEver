package com.nokia.scbe.bestdayever.model;

import java.util.List;

public class MyBestDay {
	List<Entry> entries;

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "MyBestDay [entries=" + entries + "]";
	}
	
}
