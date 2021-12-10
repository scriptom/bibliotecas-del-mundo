package org.bibliotecasmundo.shared.application.query;

import java.util.List;

public interface QueryResponse<T> {
    String getResponseString();
    Language getLanguage();
    String[] getResultTerms();
    List<T> getResults();
}
