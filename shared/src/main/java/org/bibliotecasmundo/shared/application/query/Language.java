package org.bibliotecasmundo.shared.application.query;

public interface Language {
    String getCommonName();
    String getParameterName(QueryParameter parameter);
    Query translateFromCommonLanguage(String query) throws UntranslatableQueryException;
    String translateToCommonLanguage(Query query) throws UntranslatableQueryException;
    <T> QueryResponse<T> translateResponseFromCommonLanguage(String response) throws UntranslatableQueryResponseException;
    <T> String translateResponseToCommonLanguage(QueryResponse<T> response) throws UntranslatableQueryResponseException;
    static Language commonLanguage() {
        return Z39Language.getInstance();
    }
}
