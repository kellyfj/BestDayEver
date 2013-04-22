package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoverHereResults implements Serializable {

	private static final long serialVersionUID = -5630955098831293979L;

	private DiscoverHereListOfPlaces results;

		public DiscoverHereListOfPlaces getResults() {
			return results;
		}

		public void setResults(DiscoverHereListOfPlaces results) {
			this.results = results;
		}		
		
}
