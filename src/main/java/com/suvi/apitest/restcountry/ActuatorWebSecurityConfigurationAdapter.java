package com.suvi.apitest.restcountry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web security configuration for actuator
 * @author Suvajit Chakraborty
 */
@Configuration
public class ActuatorWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ActuatorWebSecurityConfigurationAdapter.class);

    /**
     * Configure actuator security
     *
     * @param http Http Security
     * @throws Exception exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                .anyRequest().hasRole("manager").and().httpBasic();
        logger.info("http security:" + http.toString());
    }
}
