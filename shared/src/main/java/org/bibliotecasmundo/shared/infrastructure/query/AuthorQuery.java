package org.bibliotecasmundo.shared.infrastructure.query;

import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.application.query.QueryParameter;

@EqualsAndHashCode(callSuper = true)
public class AuthorQuery extends BaseQuery {
    public AuthorQuery(String searchTerm, QueryLanguage queryLanguage) {
        super(searchTerm, queryLanguage);
    }

    @Override
    public String getQueryString() {
        return queryLanguage.getParameterName(QueryParameter.AUTHOR) + " " + searchTerm;
    }
}
