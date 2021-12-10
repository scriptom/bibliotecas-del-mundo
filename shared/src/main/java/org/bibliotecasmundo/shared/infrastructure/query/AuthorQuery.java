package org.bibliotecasmundo.shared.infrastructure.query;

import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.QueryParameter;

@EqualsAndHashCode(callSuper = true)
public class AuthorQuery extends BaseQuery {
    public AuthorQuery(String searchTerm, Language language) {
        super(searchTerm, language);
    }

    @Override
    public String getQueryString() {
        return language.getParameterName(QueryParameter.AUTHOR) + " " + searchTerm;
    }
}
