package com.cognizant.springlearn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.SpringLearnApplicationZuul;
import com.cognizant.springlearn.exceptions.CountryNotFoundException;
import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.model.Greeting;

@RestController
public class GreetingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplicationZuul.class);
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/countries")
	public List<Country> getCountries( ) {
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		List<Country> list = new ArrayList<>();
		list = (List<Country>) context.getBean("countryList", List.class);
		return list;
	}
	
	@PostMapping(value="/countries",consumes = "application/json")
	public Country addCountry(@RequestBody Country country) {
		LOGGER.info("Start");
		return country;
	}
	
	
	@PostMapping("/countries/{code}")
	public Country getCountriesByCode(@PathVariable String code) throws CountryNotFoundException {
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		List<Country> list = new ArrayList<>();
		list = (List<Country>) context.getBean("countryList", List.class);
		for (Country country : list) {
			if(country.getCode().equalsIgnoreCase(code))
				return country;
		}
		throw new CountryNotFoundException();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/country")
	public Country getCountryIndia() {
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		Country country = (Country) context.getBean("in", Country.class);
		return country;
	}
}