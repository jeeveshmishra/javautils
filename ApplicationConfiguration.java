package com.patientsky.config;

import com.patientsky.common.utils.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplicationConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    private Map<String, String> propertiesMap;

    public ApplicationConfiguration() {
        propertiesMap = new HashMap<>();
    }

    public static ApplicationConfiguration fromFiles(String... files) throws Exception {
        return fromFiles(Sequence.of(files).toList());
    }

    public static ApplicationConfiguration fromFiles(List<String> files) throws Exception {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        LOG.trace("Reading from files: {}", files);
        for (String fileName: files) {
            applicationConfiguration.propertiesMap.putAll(fromFile(fileName));
        }
        return applicationConfiguration;
    }

    public static Map<String, String> fromFile(String fileName) throws Exception {
        try {
            InputStream inputStream = ApplicationConfiguration.class.getResourceAsStream(fileName);
            Properties fileProperties = new Properties();
            fileProperties.load(inputStream);

            return new HashMap<String, String>((Map) fileProperties);

        } catch (IOException ioException ) {
            LOG.error("Exception occured while loading properties: {}", ioException);
            throw new Exception(ioException);
        }
    }

    public Set<String> lookupAllKeys(){
        return propertiesMap.keySet();
    }

    public String lookupPropertyValue(String key){
        return this.propertiesMap.get(key);
    }

    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    public ApplicationConfiguration setPropertiesMap(Map<String, String> propertiesMap) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();

        applicationConfiguration.propertiesMap = propertiesMap;

        return applicationConfiguration;
    }
}
