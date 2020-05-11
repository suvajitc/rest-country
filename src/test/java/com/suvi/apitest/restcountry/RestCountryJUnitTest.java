package com.suvi.apitest.restcountry;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Junit Test Class for Individual Tests
 * @author Suvajit Chakraborty
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestCountryJUnitTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(RestCountryJUnitTest.class);

    @LocalServerPort
    private int localServerPort;

    @Test
    public void testGetIndiaByNameFullTextTrue() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=India&fullText=true";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("India", map.get("name"));
        Assert.assertEquals("New Delhi", map.get("capital"));
    }

    @Test
    public void testGetUSAByNameFullTextTrue() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=USA&fullText=true";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("United States of America", map.get("name"));
        Assert.assertEquals("Washington, D.C.", map.get("capital"));
    }

    @Test
    public void testGetIndiaByNameFullTextFalse() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=India&fullText=false";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // May return multiple records
        Assert.assertTrue(countries.size() >= 1);
        boolean found = false;
        for (Map<String, String> map : countries) {
            if (map.containsKey("name") && map.get("name").equalsIgnoreCase("India")) {
                Assert.assertTrue(map.containsKey("capital"));
                Assert.assertEquals("New Delhi", map.get("capital"));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
    }

    @Test
    public void testGetUSAByNameFullTextFalse() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=USA&fullText=false";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // May return multiple records
        Assert.assertTrue(countries.size() >= 1);
        boolean found = false;
        for (Map<String, String> map : countries) {
            if (map.containsKey("name") && map.get("name").equalsIgnoreCase("United States of America")) {
                Assert.assertTrue(map.containsKey("capital"));
                Assert.assertEquals("Washington, D.C.", map.get("capital"));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
    }

    @Test
    public void testGetNoCountryWhenSearchedByInvalidNameFullTextTrue() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=Invalid&fullText=true";
        List<Map<String, String>> countries = super.runRestCall(url);
        Assert.assertEquals(0, countries.size());
    }

    @Test
    public void testGetNoCountryWhenSearchedByInvalidNameFullTextFalse() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/name?search=Invalid&fullText=false";
        List<Map<String, String>> countries = super.runRestCall(url);
        Assert.assertEquals(0, countries.size());
    }

    @Test
    public void testGetIndiaBy2DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=IN";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("IN", map.get("alpha2Code"));
        Assert.assertEquals("New Delhi", map.get("capital"));
    }

    @Test
    public void testGetUSABy2DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=US";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("US", map.get("alpha2Code"));
        Assert.assertEquals("Washington, D.C.", map.get("capital"));
    }

    @Test
    public void testGetIndiaBy3DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=IND";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("IND", map.get("alpha3Code"));
        Assert.assertEquals("New Delhi", map.get("capital"));
    }

    @Test
    public void testGetUSABy3DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=USA";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals("USA", map.get("alpha3Code"));
        Assert.assertEquals("Washington, D.C.", map.get("capital"));
    }
}
