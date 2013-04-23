package com.nokia.scbe.bestdayever.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nokia.scbe.hackathon.bestdayever.placesapi.PlaceResultItem;
import com.nokia.scbe.hackathon.bestdayever.placesapi.PlacesAPIService;

public class Engine {
	private static final int NUM_EXTRA_PLACES_PER_CATEGORY = 20;
	public final static String ENERGY_HIGH = "HIGH";
	public final static String ENERGY_MED = "MED";
	public final static String ENERGY_LOW = "LOW";
	private static PlacesAPIService service =null;
	

    //Sample call e.optimize(42.3821,-71.0244,55.0, "rain", ENERGY_MED);
	public List<PlaceResultItem> optimize(double lat, double longitude, double temp, String forecast, String energyLevel)
	{		
		//1. Get Places Nearby
              
        List<PlaceResultItem> unfilteredList = service.getPlacesNearHere(lat, longitude, 100, null);
        
        System.out.println("Got "+unfilteredList.size()+" results back");
        List<PlaceResultItem> filteredList = filterSkippedCategories(unfilteredList);
        
        
        if(filteredList.size() < 40)
        {
        	System.out.println("Not enough places returned. Getting more places. . . .  ");
        	filteredList = getMorePlacesForEnergyLevel(lat,longitude, energyLevel, filteredList);
        }
        
        int j=1;
        for(PlaceResultItem p: filteredList)
        {
        	System.out.println("Place "+j+" : "+p.getTitle() +" is of type "+ (p.getCategory() == null ? "NO CAT" : p.getCategory().getTitle()));
        	j++;
        }
 
        
    //2. Get Calendar free time (free time chunks)
   		
   	//3. Get weather  (min temp, rain)
        filteredList = weightResultsForWeather(temp, forecast, filteredList);
        
        // r=0;
        //FOR EACH free chunk
        	//	Create Object for free chunk with filteredList.get(r);
        	//  Add to result list
        	// r++
        //END FOR
        //
        
        return filteredList;
	}
	
	private List<PlaceResultItem> weightResultsForWeather(double temp, String forecast,
			List<PlaceResultItem> filteredList) {
		//@TODO: Implement this 
		return filteredList;
	}

	/**
	 * 
	 * @param energyLevel
	 * @param filteredList
	 */
	private List<PlaceResultItem> getMorePlacesForEnergyLevel(double lat, double longitude, String energyLevel, List<PlaceResultItem> original) {
		
		 System.out.println("Getting more "+energyLevel+ " energey level places");
		 String categoryID =null;
		 List<PlaceResultItem> temp1 = new ArrayList<PlaceResultItem>();
		 List<PlaceResultItem> temp2 = new ArrayList<PlaceResultItem>();
		 
		 if(ENERGY_HIGH.equals(energyLevel))
		 {
			 categoryID = HI_ENERGY_CATEGORIES.get(0);
			 temp1 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp1.size());
			 
			 categoryID = HI_ENERGY_CATEGORIES.get(1);
			 temp2 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp2.size());
			 //TODO get more categories.
		 } else if(ENERGY_MED.equals(energyLevel))
		 {
			 categoryID = MED_ENERGY_CATEGORIES.get(0);
			 temp1 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp1.size());
			 
			 categoryID = MED_ENERGY_CATEGORIES.get(1);
			 temp2 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp2.size());
			 //TODO get more categories.
		 }
		 else if(ENERGY_LOW.equals(energyLevel))
		 {
			 categoryID = LOW_ENERGY_CATEGORIES.get(0);
			 temp1 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp1.size());
			 
			 categoryID = LOW_ENERGY_CATEGORIES.get(1);
			 temp2 = service.getPlacesNearHere(lat,longitude, NUM_EXTRA_PLACES_PER_CATEGORY, categoryID);
			 System.out.println("Searching for "+categoryID+" places return "+temp2.size());
			 
			 //TODO get more categories
		 }
		 
		 return merge(original,temp1,temp2);		 
	}

	
	private List<PlaceResultItem> merge(List<PlaceResultItem> original, List<PlaceResultItem> tempList1,
			List<PlaceResultItem> tempList2) {
		
		for(PlaceResultItem p1 : tempList1)
		{
			if(!original.contains(p1))
			{
				original.add(p1);
			}
		}
		for(PlaceResultItem p2 : tempList2)
		{
			if(!original.contains(p2))
			{
				original.add(p2);
			}
		}	
		return original;
	}

	private List<PlaceResultItem> filterSkippedCategories(List<PlaceResultItem> unfilteredList) {
        
        List<PlaceResultItem> filteredList = new ArrayList<PlaceResultItem>();
        for(PlaceResultItem p: unfilteredList)
        {
        	
        	//System.out.println("Place "+i+" : "+p.getTitle() +" is of type "+ (p.getCategory() == null ? "NO CAT" : p.getCategory().getTitle()))
        	if(p.getCategory()!=null)
        	{
        		String categoryTitle = p.getCategory().getTitle();
        		if(!CATEGORIES_TO_SKIP.contains(categoryTitle))
        		{
        			filteredList.add(p);
        		}
        	}
        }
		
        System.out.println("Got "+filteredList.size()+" results after filtering");
		return filteredList;
	}

	
	//ENERGY LEVEL CATEGORIES
	private static List<String> LOW_ENERGY_CATEGORIES = new ArrayList<String>();
	private static List<String> MED_ENERGY_CATEGORIES = new ArrayList<String>();
	private static List<String> HI_ENERGY_CATEGORIES = new ArrayList<String>();

	
	static{
		LOW_ENERGY_CATEGORIES.add("bookshop");
		LOW_ENERGY_CATEGORIES.add("library");
		LOW_ENERGY_CATEGORIES.add("cinema");
		LOW_ENERGY_CATEGORIES.add("theatre-music-culture");
		
		MED_ENERGY_CATEGORIES.add("leisure-outdoor");
		MED_ENERGY_CATEGORIES.add("recreation");
		
		HI_ENERGY_CATEGORIES.add("amusement-holiday-park");
		HI_ENERGY_CATEGORIES.add("mountain-hill");
	}

	//WEATHER RELATED CATEGORIES
	private static List<String> GOOD_WEATHER_CATEGORIES = new ArrayList<String>();
	private static List<String> OK_WEATHER_CATEGORIES = new ArrayList<String>();
	private static List<String> BAD_WEATHER_CATEGORIES = new ArrayList<String>();

	static{
		BAD_WEATHER_CATEGORIES.add("shopping");
		BAD_WEATHER_CATEGORIES.add("library");
		BAD_WEATHER_CATEGORIES.add("cinema");
		BAD_WEATHER_CATEGORIES.add("theatre-music-culture");
		
		OK_WEATHER_CATEGORIES.add("recreation");
		OK_WEATHER_CATEGORIES.add("sports-facility-venue");

		GOOD_WEATHER_CATEGORIES.add("body-of-water");
		GOOD_WEATHER_CATEGORIES.add("forest-heath-vegetation");
		GOOD_WEATHER_CATEGORIES.add("leisure-outdoor");
		GOOD_WEATHER_CATEGORIES.add("recreation");
	}
	
	private static Set<String> CATEGORIES_TO_SKIP = new HashSet<String>();

	static{
		CATEGORIES_TO_SKIP.add("Bar/Pub");
		CATEGORIES_TO_SKIP.add("Casino");
		CATEGORIES_TO_SKIP.add("Coffee/Tea");
		CATEGORIES_TO_SKIP.add("Dance or Nightclub");
		CATEGORIES_TO_SKIP.add("Department store");
		CATEGORIES_TO_SKIP.add("Eat & Drink");
		CATEGORIES_TO_SKIP.add("Facility");
		CATEGORIES_TO_SKIP.add("Food & Drink");
		CATEGORIES_TO_SKIP.add("Going Out");
		CATEGORIES_TO_SKIP.add("Hardware, House & Garden");
		CATEGORIES_TO_SKIP.add("Transport");
		CATEGORIES_TO_SKIP.add("Airport");
		CATEGORIES_TO_SKIP.add("Railway station");
		CATEGORIES_TO_SKIP.add("Public transport");
		CATEGORIES_TO_SKIP.add("Ferry terminal");
		CATEGORIES_TO_SKIP.add("Taxi stand");
		CATEGORIES_TO_SKIP.add("Accommodation");
		CATEGORIES_TO_SKIP.add("Hotel");
		CATEGORIES_TO_SKIP.add("Motel");
		CATEGORIES_TO_SKIP.add("Hostel");
		CATEGORIES_TO_SKIP.add("Camping");
		CATEGORIES_TO_SKIP.add("Kiosk/24-7/Convenience store");
		CATEGORIES_TO_SKIP.add("Pharmacy");
		CATEGORIES_TO_SKIP.add("Electronics");
		CATEGORIES_TO_SKIP.add("Clothing & Accessories");
		CATEGORIES_TO_SKIP.add("Shop");
		CATEGORIES_TO_SKIP.add("Business & Services");
		CATEGORIES_TO_SKIP.add("ATM/Bank/Exchange");
		CATEGORIES_TO_SKIP.add("Police/Emergency");
		CATEGORIES_TO_SKIP.add("Post office");
		CATEGORIES_TO_SKIP.add("Gas station");
		CATEGORIES_TO_SKIP.add("Car rental");
		CATEGORIES_TO_SKIP.add("Car Dealer/Repair");
		CATEGORIES_TO_SKIP.add("Travel Agency");
		CATEGORIES_TO_SKIP.add("Communication/Media");
		CATEGORIES_TO_SKIP.add("Business/Industry");
		CATEGORIES_TO_SKIP.add("Service");
		CATEGORIES_TO_SKIP.add("Facilities");
		CATEGORIES_TO_SKIP.add("Hospital or Health Care Facility");
		CATEGORIES_TO_SKIP.add("Government or Community Facility");
		CATEGORIES_TO_SKIP.add("Education Facility");
		CATEGORIES_TO_SKIP.add("Parking Facility");
		CATEGORIES_TO_SKIP.add("Public Toilet/Rest Area");
		CATEGORIES_TO_SKIP.add("Religious place");
		CATEGORIES_TO_SKIP.add("Restaurant");
		CATEGORIES_TO_SKIP.add("Administrative areas/buildings");
		CATEGORIES_TO_SKIP.add("Administrative Region");
		CATEGORIES_TO_SKIP.add("City, Town or Village");
		CATEGORIES_TO_SKIP.add("Snacks/Fast food");
		CATEGORIES_TO_SKIP.add("Building");
		CATEGORIES_TO_SKIP.add("Tourist information");
		CATEGORIES_TO_SKIP.add("Undersea Feature");;
	}

}
