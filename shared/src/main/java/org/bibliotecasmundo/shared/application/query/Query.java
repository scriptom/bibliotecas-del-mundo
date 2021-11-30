package org.bibliotecasmundo.shared.application.query;

public interface Query {
    QueryLanguage getLanguage();
    String getQueryString();
    String getSearchTerm();
}
