package org.bibliotecasmundo.shared.infrastructure.config.impl;

import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesAppConfig implements AppConfig {
    private final Properties properties;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected PropertiesAppConfig(Properties properties) {
        this.properties = properties;
    }

    public static AppConfig fromProperties(Properties properties) {
        return new PropertiesAppConfig(properties);
    }

    public static AppConfig fromPropertiesPath(String propertiesPath) throws IOException {
        final Properties properties = new Properties();
        properties.load(PropertiesAppConfig.class.getClassLoader().getResourceAsStream(propertiesPath));
        return fromProperties(properties);
    }

    @Override
    public String getConfigParam(String paramName) {
        logger.debug("Getting config param: {}", paramName);
        return getConfigParam(paramName, null);
    }

    @Override
    public String getConfigParam(String paramName, String defaultValue) {
        logger.debug("Getting config param: {} with default {}", paramName, defaultValue);
        if (!properties.containsKey(paramName)) {
            logger.warn("Config param {} not found", paramName);
        }
        return properties.getProperty(paramName, defaultValue);
    }
}
