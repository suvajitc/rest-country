package com.suvi.apitest.restcountry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * JUnit Test Class for parameterized tests
 * @author Suvajit Chakraborty
 */
@RunWith(Parameterized.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestCountryParameterizedJUnitTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(RestCountryParameterizedJUnitTest.class);

    @Autowired
    private WebApplicationContext wac;

    @LocalServerPort
    private int localServerPort;

    @Parameterized.Parameter(value = 0)
    public String search;

    @Parameterized.Parameter(value = 1)
    public String name;

    @Parameterized.Parameter(value = 2)
    public String code2Digit;

    @Parameterized.Parameter(value = 3)
    public String code3Digit;

    @Parameterized.Parameter(value = 4)
    public String capital;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{"USA", "United States of America", "US", "USA", "Washington, D.C."});
        params.add(new Object[]{"India", "India", "IN", "IND", "New Delhi"});
        params.add(new Object[]{"France", "France", "FR", "FRA", "Paris"});
        params.add(new Object[]{"Brazil", "Brazil", "BR", "BRA", "Brasília"});

        return params;
    }

    @Before
    public void setup() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    @Test
    public void testGetCountryByNameFullTextTrue() {
        final String url = "http://localhost:" + localServerPort
                + "/v1/countries/name?search=" + search + "&fullText=true";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals(name, map.get("name"));
        Assert.assertEquals(capital, map.get("capital"));
    }

    @Test
    public void testGetCountryByNameFullTextFalse() {
        final String url = "http://localhost:" + localServerPort
                + "/v1/countries/name?search=" + search + "&fullText=false";
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // May return multiple records
        Assert.assertTrue(countries.size() >= 1);
        boolean found = false;
        for (Map<String, String> map : countries) {
            if (map.containsKey("name") && map.get("name").equalsIgnoreCase(name)) {
                Assert.assertTrue(map.containsKey("capital"));
                Assert.assertEquals(capital, map.get("capital"));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
    }

    @Test
    public void testGetCountryBy2DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=" + code2Digit;
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals(code2Digit, map.get("alpha2Code"));
        Assert.assertEquals(capital, map.get("capital"));
    }

    @Test
    public void testGetCountryBy3DigitCode() {
        final String url = "http://localhost:" + localServerPort + "/v1/countries/code?search=" + code3Digit;
        List<Map<String, String>> countries = super.runRestCall(url);
        assert countries != null;
        // Should return only one record
        Assert.assertEquals(1, countries.size());
        Map<String, String> map = countries.get(0);
        Assert.assertEquals(code3Digit, map.get("alpha3Code"));
        Assert.assertEquals(capital, map.get("capital"));
    }
}
