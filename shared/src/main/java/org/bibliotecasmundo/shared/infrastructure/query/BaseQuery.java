package org.bibliotecasmundo.shared.infrastructure.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;

@AllArgsConstructor
@EqualsAndHashCode(of = { "queryLanguage" })
public abstract class BaseQuery implements Query {
    protected final String searchTerm;
    protected final QueryLanguage queryLanguage;

    @Override
    public QueryLanguage getLanguage() {
        return queryLanguage;
    }

    @Override
    public String getSearchTerm() {
        return searchTerm;
    }
}
