package com.suvi.apitest.restcountry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suvi.apitest.restcountry.pojo.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controller class to return data from spring boot
 * @author Suvajit Chakraborty
 */
@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper mapper;

    public CountryController() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Returns countries from search by name
     *
     * @param search search string containing name of country
     * @param fullText flag denoting if full text search is true or false
     *
     * @return List
     */
    @GetMapping("/name")
    public List<Country> getCountryByName(
            @RequestParam(value = "search", defaultValue = "USA") String search,
            @RequestParam(value = "fullText", defaultValue = "true") String fullText
    ) {
        String rawJson = null;
        try {
            rawJson = restTemplate.getForObject(
                    "https://restcountries.eu/rest/v2/name"
                            + "/" + search + "?fullText=" + fullText,
                    String.class
            );
        } catch (Exception ex) {
            logger.error("error in getCountryByName", ex);
        }
        return getParsedData(rawJson);
    }

    /**
     * Returns countries from search by code
     *
     * @param search search string containing code of country
     *
     * @return List
     */
    @GetMapping("/code")
    public List<Country> getCountryByCode(
            @RequestParam(value = "search", defaultValue = "USA") String search
    ) {
        String rawJson = null;
        try {
            rawJson = restTemplate.getForObject(
                    "https://restcountries.eu/rest/v2/alpha?codes=" + search,
                    String.class
            );
        } catch (Exception ex) {
            logger.error("error in getCountryByCode", ex);
        }
        return getParsedData(rawJson);
    }

    private List<Country> getParsedData(String rawJson) {
        try {
            if (checkSuccess(rawJson)) {
                return parseCountryData(rawJson);
            } else {
                // Send a country data with name no data found
                return new ArrayList<>();
            }
        } catch (JsonProcessingException e) {
            logger.error("error in controller", e);
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkSuccess(String rawJson) {
        return rawJson != null;
    }

    private List<Country> parseCountryData(String rawJson) throws JsonProcessingException {
        assert rawJson != null;
        logger.debug("rawJson:" + rawJson);
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

