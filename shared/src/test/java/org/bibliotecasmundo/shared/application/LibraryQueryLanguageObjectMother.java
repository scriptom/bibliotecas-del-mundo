package org.bibliotecasmundo.shared.application;

import com.google.common.collect.ImmutableMap;
import org.bibliotecasmundo.shared.application.query.LibraryLanguage;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;

public class LibraryQueryLanguageObjectMother {
    public static Language spanishLibraryQueryLanguage() {
        final AppConfig appConfig = AppConfig.createFromMap(
                ImmutableMap.<String, String>builder()
                        .put(AppConfig.QUERY_TOKENS_TITLE, "Buscar Titulo")
                        .put(AppConfig.QUERY_TOKENS_AUTHOR, "Buscar Autor")
                        .build()
        );
        return LibraryLanguage.buildFromConfiguration("Biblioteca en Español", appConfig);
    }
}
