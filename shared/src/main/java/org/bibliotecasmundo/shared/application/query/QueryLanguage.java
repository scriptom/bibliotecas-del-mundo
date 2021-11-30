package org.bibliotecasmundo.shared.application.query;

import org.bibliotecasmundo.shared.infrastructure.language.Z39Language;

public interface QueryLanguage {
    String getCommonName();
    String getParameterName(QueryParameter parameter);
    Query translateFromCommonLanguage(String query) throws UntranslatableQueryException;
    String translateToCommonLanguage(Query query) throws UntranslatableQueryException;
    static QueryLanguage commonLanguage() {
        return Z39Language.getInstance();
    }
}
