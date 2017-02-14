package com.patientsky.systemtests.config;

import com.patientsky.config.ApplicationConfiguration;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ApplicationConfigurationTest {
    private String file1 = "/application.properties";
    private String file2 = "/config_override/application_override.properties";

    @Test
    public void testFromArrayOfFiles() throws Exception {
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.fromFiles(file1, file2);
        assertTrue(applicationConfiguration.getPropertiesMap().containsKey("environment.jdbc.driver"));
        assertEquals(applicationConfiguration.lookupPropertyValue("environment.jdbc.driver"), "org.sqlite.JDBC");
    }

    @Test
    public void testFromListOfFiles() throws Exception {
        List<String> filesList = new ArrayList<>();
        filesList.add(file1);
        filesList.add(file2);

        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.fromFiles(filesList);
        assertTrue(applicationConfiguration.getPropertiesMap().containsKey("environment.jdbc.driver"));
        assertEquals(applicationConfiguration.lookupPropertyValue("environment.jdbc.driver"), "org.sqlite.JDBC");
    }

    @Test
    public void testLookupAllKeys() throws Exception {
        List<String> filesList = new ArrayList<>();
        filesList.add("/application.properties");
        filesList.add("/config_override/application_override.properties");

        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.fromFiles(filesList);
        assertTrue(applicationConfiguration.lookupAllKeys().contains("environment.jdbc.driver"));
    }

    @Test
    public void testLookupPropertyValue() throws Exception {
        List<String> filesList = new ArrayList<>();
        filesList.add("/application.properties");
        filesList.add("/config_override/application_override.properties");

        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.fromFiles(filesList);
        assertNotNull(applicationConfiguration.getPropertiesMap());
        assertNotNull(applicationConfiguration.lookupPropertyValue("environment.jdbc.driver"));
    }

}