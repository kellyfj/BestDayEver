package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    	Set<String> distinctCategories = new HashSet<String>();
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath*:**/services.xml");

        ClientFactory clientFactory = (ClientFactory) context.getBean("clientFactory");
        PlacesAPIClient client = clientFactory.createPlacesAPIClient();
        
        PlacesAPIService service = new PlacesAPIService(client);
        List<PlaceResultItem> list = service.getPlacesNearHere(42.3821,-71.0244, 100, null);
        
        System.out.println("Got "+list.size()+" results back");
        int i=1;
        for(PlaceResultItem p: list)
        {
        	
        	System.out.println("Place "+i+" : "+p.getTitle() +" is of type "+ (p.getCategory() == null ? "NO CAT" : p.getCategory().getTitle()));
        	i++;
        	if(p.getCategory()!=null)
        	{
        		String categoryTitle = p.getCategory().getTitle();
        		if(!distinctCategories.contains(categoryTitle))
        		{
        			distinctCategories.add(categoryTitle);
        		}
        	}
        }
        
        System.out.println("\nDistinct Categories::");
        for (String s : distinctCategories) {
            System.out.println(s);
        }
        
    }
}
