package com.nokia.scbe.hackathon.bestdayever.placesapi;

import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 
 */
public interface PlacesAPIClient {
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/places/v1/places/{placeId}")
    ClientResponse<Object> getPlace(@HeaderParam("Accept") String mediaType, @PathParam("placeId") String placeId, @QueryParam("app_id") String appId, @QueryParam("app_code") String appCode);

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/places/v1/discover/explore")
    ClientResponse<Object>  discoverExplore(@HeaderParam("Accept") String mediaType, @QueryParam("at") String currentLocation, @QueryParam("tf") String textFormat, @QueryParam("size") Integer size, @QueryParam("cat") String category, @QueryParam("app_id") String appId, @QueryParam("app_code") String appCode);
}


