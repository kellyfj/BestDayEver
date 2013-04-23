package com.nokia.scbe.bestdayever.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nokia.scbe.bestdayever.model.InputConstraints;
import com.nokia.scbe.bestdayever.model.MyBestDay;

@Controller
public class BestDayEverController {
	
	Engine engine;
	
	@Autowired
	public void setEngine(Engine e) {
		this.engine = e;
	}

	// query string: latlong1=42.36225,-71.0783688&
	// interval=2013-04-22T10:00:00.000-04:00,2013-04-22T21:00:00.000-04:00&
	// effort=HIGH& cost=75&
	// lunch=2013-04-22T12:30:00.000-04:00,2013-04-22T14:00:00.000-04:00&
	// dinner=2013-04-22T18:30:00.000-04:00,2013-04-22T20:00:00.000-04:00

	@RequestMapping("/mybestday")
	public @ResponseBody MyBestDay getMyBestDay(@RequestParam(value = "latlong1", required = true) String latlong,
			@RequestParam(value="interval", required=true) String interval, @RequestParam(value="effort", required=true) String effort,
			@RequestParam(value="googleId", required=false) String googleId, @RequestParam(value="noaId", required=false) String noaId,
			@RequestParam(value="cost", required=true) String cost) {
		
		//parse to get lat long
		String[] tokens = StringUtils.split(latlong, ",");
		if ((tokens == null) || (tokens.length != 2)) {
			throw new IllegalArgumentException("lat long pair is not present: "+ latlong);
		}
		
		double lat = Double.parseDouble(tokens[0]);	
		double lon = Double.parseDouble(tokens[1]);
		
		//parse to get time interval
		String[] intervalTokens = StringUtils.split(interval, ",");
		if ((tokens == null) || (tokens.length != 2)) {
			throw new IllegalArgumentException("time interval is not present: "+ interval);
		}
		
		Date start = null;
		Date end = null;
		
		try {
			start =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(intervalTokens[0]);	
			end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(intervalTokens[1]);
		} catch (ParseException e) {
			 start = new Date();
			 end = getEnd(start);
		}
		
		//if there is no googleId provided use the default
		if (googleId == null) {
			googleId = "bestdayever.hackethon@gmail.com";
		}
		
		
		InputConstraints constraints = new InputConstraints();
		constraints.setCost(cost);
		constraints.setGoogleId(googleId);
		constraints.setNoaId(noaId);
		constraints.setLatitute(lat);
		constraints.setLongitude(lon);
		constraints.setStart(start);
		constraints.setEnd(end);
		constraints.setEffort(effort);
		
		System.out.println(constraints.toString());
		
		//Buisness logic goes here
		MyBestDay bestDay = engine.createMyBestDay(constraints.getGoogleId(), constraints.getStart(), constraints.getEnd(),
				constraints.getTimezone(), constraints.getLatitute(), constraints.getLongitude(), 0.0D, null, constraints.getEffort());
		
		return bestDay;
	}

	public static Date getEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
}
