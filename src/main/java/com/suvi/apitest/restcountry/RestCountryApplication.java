package com.suvi.apitest.restcountry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suvi.apitest.restcountry.pojo.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Main Springboot application
 * @author Suvajit Chakraborty
 */
@SpringBootApplication
public class RestCountryApplication {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(RestCountryApplication.class);

	/**
	 * Main method
	 *
	 * @param args command line args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestCountryApplication.class, args);
	}

	/**
	 * Rest template bean
	 * @param builder rest template builder
	 * @return rest template object
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * Command Line runner bean, runs command line springboot invokation
	 * @param restTemplate rest template
	 * @return Command line runner
	 */
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			Map<String, String> commandLineArgs = getArgumentValues(args);
			String searchMode = commandLineArgs.get("searchMode");
			String searchString = commandLineArgs.get("searchString");
			String fullTextMode = commandLineArgs.get("fullTextMode");
			String rawJson = "";
			try {
				if (searchMode.equals("name")) {
					rawJson = restTemplate.getForObject(
							"https://restcountries.eu/rest/v2/"
									+ searchMode + "/" + searchString + "?fullText=" + fullTextMode,
							String.class
					);
				} else if (searchMode.equals("code")) {
					rawJson = restTemplate.getForObject(
							"https://restcountries.eu/rest/v2/alpha?codes=" + searchString,
							String.class
					);
				} else {
					rawJson = "";
				}
			} catch (Exception ex) {
				logger.error("error", ex);
				rawJson = null;
			}
			if (checkSuccess(rawJson)) {
				List<Country> countries = parseCountryData(rawJson);
				for (Country country : countries) {
					logger.info("Country Results:");
					logger.info("Country:" + country.toString());
				}
			} else {
				logger.info("Country Results: No Data Found");
			}
		};
	}

	private Map<String, String> getArgumentValues(String [] args) {
		Map<String, String> commandLineArgs = new HashMap<>();
		String searchMode = "name";
		String searchString = "USA";
		String fullTextMode = "true";
		for (String arg: args) {
			logger.info("arg:" + arg);
			// Split the arg by comma first
			String[] keyVals = arg.split(",");
			for (String keyVal : keyVals) {
				String[] pair = keyVal.split("=");
				String key = pair[0];
				String value = pair[1];
				switch (key) {
					case "--fullText":
						fullTextMode = value;
						break;
					case "--search":
						searchString = value;
						break;
					case "--mode":
						searchMode = value;
						break;
				}
			}
		}
		logger.info("searchMode:" + searchMode);
		logger.info("searchString:" + searchString);
		logger.info("fullTextMode:" + fullTextMode);
		commandLineArgs.put("searchMode", searchMode);
		commandLineArgs.put("searchString", searchString);
		commandLineArgs.put("fullTextMode", fullTextMode);
		return commandLineArgs;
	}

	private boolean checkSuccess(String rawJson) {
		return rawJson != null;
	}

	private List<Country> parseCountryData(String rawJson) throws JsonProcessingException {
		assert rawJson != null;
		logger.debug("rawJson:" + rawJson);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode arrayJsonNode = mapper.readTree(rawJson);
		List<Country> countries = new ArrayList<>();
		for (Iterator<JsonNode> it = arrayJsonNode.elements(); it.hasNext();) {
			JsonNode jsonNode = it.next();
			Country country = new Country();
			country.setName(jsonNode.get("name").textValue());
			country.setCapital(jsonNode.get("capital").textValue());
			country.setRegion(jsonNode.get("region").textValue());
			country.setCountryCode2Digit(jsonNode.get("alpha2Code").textValue());
			country.setCountryCode3Digit(jsonNode.get("alpha3Code").textValue());
			country.setSubregion(jsonNode.get("subregion").textValue());
			country.setArea(jsonNode.get("area").doubleValue());
			country.setPopulation(jsonNode.get("population").intValue());
			logger.debug("country:" + country.toString());
			countries.add(country);
		}
		return countries;
	}
}
