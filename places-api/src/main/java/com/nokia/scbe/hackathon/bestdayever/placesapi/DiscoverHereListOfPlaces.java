package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoverHereListOfPlaces implements Serializable{

	private static final long serialVersionUID = 8954508442695316846L;

	private List<PlaceResultItem> placeResultItems = null;

	public List<PlaceResultItem> getItems() {
		return placeResultItems;
	}

	public void setItems(List<PlaceResultItem> placeResultItems) {
		this.placeResultItems = placeResultItems;
	}
	
}
