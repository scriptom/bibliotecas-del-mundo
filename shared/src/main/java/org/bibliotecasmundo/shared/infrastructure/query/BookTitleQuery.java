package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.QueryParameter;

public class BookTitleQuery extends BaseQuery {
    public BookTitleQuery(String searchTerm, Language language) {
        super(searchTerm, language);
    }

    @Override
    public String getQueryString() {
        return language.getParameterName(QueryParameter.TITLE) + " " + searchTerm;
    }
}
