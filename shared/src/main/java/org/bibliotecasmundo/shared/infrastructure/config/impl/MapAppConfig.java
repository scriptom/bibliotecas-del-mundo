package org.bibliotecasmundo.shared.infrastructure.config.impl;

import lombok.ToString;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ToString(exclude = "logger")
public class MapAppConfig implements AppConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, String> config;

    protected MapAppConfig(Map<String, String> config) {
        this.config = config;
    }

    public static AppConfig fromConfigMap(Map<String, String> config) {
        return new MapAppConfig(config);
    }

    @Override
    public String getConfigParam(String paramName) {
        return getConfigParam(paramName, null);
    }

    @Override
    public String getConfigParam(String paramName, String defaultValue) {
        if (!config.containsKey(paramName)) {
            logger.warn("Config param {} not found", paramName);
        }
        return config.getOrDefault(paramName, defaultValue);
    }
}
