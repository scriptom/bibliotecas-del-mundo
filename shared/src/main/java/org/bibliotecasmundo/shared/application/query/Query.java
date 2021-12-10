package org.bibliotecasmundo.shared.application.query;

import org.bibliotecasmundo.shared.application.Translatable;

public interface Query extends Translatable {
    Language getLanguage();

    String getQueryString();

    String getSearchTerm();

    default String translateToCommonLanguage() {
        return getLanguage().translateToCommonLanguage(this);
    }
}
