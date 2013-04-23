package callForcastRestApiInJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.nokia.solo.api.impl.JacksonSerializer;

public class CallForcastRestApiInJava {
	
	
	public static JacksonSerializer serializer;
	
	public static void main(String[] args) {
		
		   if (args.length != 3) {
			   System.out.println("Unable to process input please proveide input as latitute, longitude, unixtimestamp");
			   return;
		   }
		   String latitude = args[0];
		   String longitude = args[1];
		   String unixtimeStamp = args[2];

		   java.net.URL url;
		   String line;
		   String response = "";
		   URLConnection connection;

		   serializer = new JacksonSerializer();
		   
		   try {
		          url = new URL( 
		    		 "https://api.forecast.io/forecast/69fecca2ce6b7f3e65df166bc5c0ebde/" +
		    				 latitude + "," + longitude + "," + unixtimeStamp
		    		 );
		     connection = url.openConnection(  );
		      BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		       while ((line = rd.readLine()) != null) {
		           response += line;
		       }
		       JSONObject jsonObject = JSONObject.fromObject( response );
		       Map<String, Object> weatherMap = (HashMap<String, Object>)serializer.
		    		   convertResponseStringToObject(HashMap.class, jsonObject.toString());
		       String hourlyForcast = getHourlyForcastList(weatherMap);
		       System.out.println(hourlyForcast);
		       rd.close();
		  }
		  catch(Exception e){
		     System.out.println("Unable to process your input beacuse of error, " + e);
		  }
	}
	
	public static String getHourlyForcastList(Map<String, Object> weatherMap) {
		if(null != weatherMap.get("hourly")) {
	    	   Map<String, Object> hourly = (Map<String, Object>) weatherMap.get("hourly");
	    	   if(null != hourly.get("data")) {
	    		   List<Map<String, Object>> data = (List<Map<String, Object>>) hourly.get("data");
	    		   String dataAsJson = serializer.convertRequestObjectToRequestBodyString(data);
	    		   return dataAsJson;
	    	   }
	       }
		return null;
	}
}