package org.bibliotecasmundo.shared.infrastructure.config;

import org.bibliotecasmundo.shared.infrastructure.config.impl.MapAppConfig;
import org.bibliotecasmundo.shared.infrastructure.config.impl.PropertiesAppConfig;

import java.io.IOException;
import java.util.Map;

public interface AppConfig {
    String APP_NAME = "app.name";
    String QUERY_TOKENS_TITLE = "query.tokens.title";
    String QUERY_TOKENS_AUTHOR = "query.tokens.author";
    String RESPONSE_TOKEN = "response.token";

    String getConfigParam(String paramName);
    String getConfigParam(String paramName, String defaultValue);

    static AppConfig createFromPropertiesFile(String propertiesFilePath) throws IOException {
        return PropertiesAppConfig.fromPropertiesPath(propertiesFilePath);
    }

    static AppConfig createFromMap(Map<String, String> map) {
        return MapAppConfig.fromConfigMap(map);
    }
}
