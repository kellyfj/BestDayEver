package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath*:**/services.xml");

        ClientFactory clientFactory = (ClientFactory) context.getBean("clientFactory");
        PlacesAPIClient client = clientFactory.createPlacesAPIClient();
        
        PlacesAPIService service = new PlacesAPIService(client);
        List<PlaceResultItem> list = service.getPlacesNearHere(42.3821,-71.0244, 10);
        
        System.out.println("Got "+list.size()+" results back");
        
    }
}
