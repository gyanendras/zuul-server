package com.cognizant.springlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cognizant.springlearn.model.Country;

@EnableZuulProxy
@EnableDiscoveryClient 
@SpringBootApplication
public class SpringLearnApplicationZuul {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplicationZuul.class);
	
	public static void main(String[] args) {
		LOGGER.info("Inside main()");
		SpringApplication.run(SpringLearnApplicationZuul.class, args);
		//displayDate();
		//displayCountry();
		LOGGER.info("End main()");
	}
	
	private static void displayCountry() {
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		List<Country> list = new ArrayList<>();
		list = (List<Country>) context.getBean("countryList", List.class);
		LOGGER.debug("Country : {}", list.toString());

	}

	public static void displayDate()
	{
		LOGGER.info("Inside displayDate()");
		ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
		SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
		try {
			LOGGER.debug("date={}",format.parse("31/12/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		LOGGER.info("End displayDate()");
	}
}
