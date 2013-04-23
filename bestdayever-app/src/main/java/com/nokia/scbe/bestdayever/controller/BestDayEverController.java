package com.nokia.scbe.bestdayever.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nokia.scbe.bestdayever.model.MyBestDay;

@Controller
public class BestDayEverController {
	@RequestMapping("/mybestday")
	public MyBestDay getMyBestDay() {
		MyBestDay bestDay = new MyBestDay();
		bestDay.setPlace("Roller Kingdom");
		bestDay.setPrice(50);
		
		return bestDay;
	}
}
