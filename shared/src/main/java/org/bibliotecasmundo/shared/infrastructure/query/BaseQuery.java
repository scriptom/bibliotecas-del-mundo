package org.bibliotecasmundo.shared.infrastructure.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.Language;

@AllArgsConstructor
@EqualsAndHashCode(of = { "language" })
public abstract class BaseQuery implements Query {
    protected final String searchTerm;
    protected final Language language;

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public String getSearchTerm() {
        return searchTerm;
    }
}
