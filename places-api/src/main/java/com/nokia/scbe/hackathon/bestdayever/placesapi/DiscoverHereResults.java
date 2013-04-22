package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoverHereResults implements Serializable {

	private static final long serialVersionUID = -5630955098831293979L;

	private List<Object> results;

		public List<Object> getResults() {
			return results;
		}

		public void setResults(List<Object> results) {
			this.results = results;
		}		
		
}
