package com.nokia.scbe.hackathon.bestdayever.placesapi;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        PlacesAPIService service = new PlacesAPIService();
        DiscoverHereListOfPlaces list = service.getPlacesNearHere(42.3821,-71.0244, 10);
    }
}
