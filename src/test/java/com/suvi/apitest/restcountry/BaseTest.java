package com.suvi.apitest.restcountry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Base Test class for common methods
 * @author Suvajit Chakraborty
 */
public class BaseTest {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Runs the rest call to local springboot
     *
     * @param url rest call url
     * @return List
     */
    protected List<Map<String, String>> runRestCall(String url) {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, String>> countries = restTemplate.getForObject(url, List.class);
        logger.debug("countries:" + countries);
        return countries;
    }
}
