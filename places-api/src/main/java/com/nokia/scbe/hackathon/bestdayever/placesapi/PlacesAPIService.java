package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.util.List;
import javax.servlet.ServletContext;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.httpclient.HttpStatus;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Path("/BestDayEver")
public class PlacesAPIService {
	   private static final Logger LOG = Logger.getLogger(PlacesAPIService.class);
	
	   private PlacesAPIClient placesClient=null;
	   private String APP_ID="gFQEBX4EWfniggcRIZl1";
	   private String APP_CODE="r16TrJ0jD6SEn8bMQRbmpw";
	   
	@GET
	@Path("/placetest")
	public Response HelloWorld(@Context ServletContext servletContext)
	{
		ApplicationContext ctx = 
                WebApplicationContextUtils.getWebApplicationContext(servletContext);

		String result = "Hello World";

		return Response.status(200).entity(result).build();
	}
	
	public DiscoverHereListOfPlaces getPlacesNearHere(double lat, double longitude, int size)
	{
    LOG.debug("Reading places from PBAPI with Lat, Long, Size");
    
    ClientResponse<Object> pbapiResponse = null;
    try {
    	String latLong = ""+lat+","+longitude;
        pbapiResponse = placesClient.discoverExplore(MediaType.APPLICATION_JSON,latLong,"text",size,APP_ID,APP_CODE);
        
        if (pbapiResponse.getStatus() == HttpStatus.SC_OK) {
            DiscoverHereResults results = (DiscoverHereResults) pbapiResponse.getEntity(new GenericType<DiscoverHereResults>() {});
            String entity = (String) pbapiResponse.getEntity();
            LOG.info("Got "+entity+"back");
            
            List<Object> nextLevelDown = results.getResults();
            DiscoverHereListOfPlaces returnMe = (DiscoverHereListOfPlaces) nextLevelDown.get(0);
            return returnMe;
        } else {
            LOG.error("Failure in getting the places PBAPI response code "+ pbapiResponse.getStatus());
            throw new RuntimeException("Response status [" + pbapiResponse.getStatus() + "] returned from PBAPI while attempting to retrieve places nearby");
        }            
    } finally {
        if (pbapiResponse != null) {
            pbapiResponse.releaseConnection();
        }
    }
}
}
