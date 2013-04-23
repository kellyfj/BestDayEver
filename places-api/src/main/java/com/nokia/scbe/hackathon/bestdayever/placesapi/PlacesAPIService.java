package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlacesAPIService {
	private static final Logger LOG = Logger.getLogger(PlacesAPIService.class);

	private PlacesAPIClient placesClient;
	private String APP_ID = "gFQEBX4EWfniggcRIZl1";
	private String APP_CODE = "r16TrJ0jD6SEn8bMQRbmpw";
	
	
	public PlacesAPIService() {
	}
	
	@Autowired
	public void setPlacesAPIClient(PlacesAPIClient client) {
		this.placesClient = client;
//        ClientFactory clientFactory = (ClientFactory) context.getBean("clientFactory");
//         placesClient = clientFactory.createPlacesAPIClient();
	}
	
	
	public List<PlaceResultItem> getPlacesNearHere(double lat, double longitude, int size, String category) {
		
		LOG.debug("Reading places from PBAPI with Lat, Long, Size");

		ClientResponse<Object> response = null;
		try {
			String latLong = "" + lat + "," + longitude;
			response = placesClient.discoverExplore(MediaType.APPLICATION_JSON,latLong, "plain", size, category, APP_ID,
					APP_CODE);

			if (response.getStatus() == HttpStatus.SC_OK) {
				
				DiscoverHereResults results = (DiscoverHereResults) response
						.getEntity(new GenericType<DiscoverHereResults>() {
						});
				System.out.println(results);

				DiscoverHereListOfPlaces returnMe = results.getResults();
				
				return returnMe.getItems();
			} else {
				System.err.println("Failure in getting the places PBAPI response code (" + response.getStatus()+") "+response.getEntity(String.class));
				throw new RuntimeException("Response status [" + response.getStatus()
						+ "] returned from PBAPI while attempting to retrieve places nearby");
			}
		} finally {
			if (response != null) {
				response.releaseConnection();
			}
		}
	}
}
