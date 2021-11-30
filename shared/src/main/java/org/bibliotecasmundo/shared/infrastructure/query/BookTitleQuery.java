package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.application.query.QueryParameter;

public class BookTitleQuery extends BaseQuery {
    public BookTitleQuery(String searchTerm, QueryLanguage queryLanguage) {
        super(searchTerm, queryLanguage);
    }

    @Override
    public String getQueryString() {
        return queryLanguage.getParameterName(QueryParameter.TITLE) + " " + searchTerm;
    }
}
