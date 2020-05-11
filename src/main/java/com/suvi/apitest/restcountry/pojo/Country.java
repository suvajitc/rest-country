package com.suvi.apitest.restcountry.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java Object denoting a Country, to be used as JsonObject
 * @author Suvajit Chakraborty
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class Country {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Country.class);

    /**
     * Fields
     */
    private String name;

    private String capital;

    private String region;

    private String subregion;

    private int population;

    private double area;

    @JsonProperty("alpha2Code")
    private String countryCode2Digit;

    @JsonProperty("alpha3Code")
    private String countryCode3Digit;

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getCountryCode2Digit() {
        return countryCode2Digit;
    }

    public void setCountryCode2Digit(String countryCode2Digit) {
        this.countryCode2Digit = countryCode2Digit;
    }

    public String getCountryCode3Digit() {
        return countryCode3Digit;
    }

    public void setCountryCode3Digit(String countryCode3Digit) {
        this.countryCode3Digit = countryCode3Digit;
    }

    /**
     * Converts to Json String
     *
     * @return String
     */
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("error in country toString", e);
            e.printStackTrace();
        }
        return null;
    }
}
