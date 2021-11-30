package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.Query;

public class QueryObjectMother {
    public static Query commonLangJKRowlingAuthorQuery() {
        return new AuthorQuery("J.K. Rowling", QueryLanguageObjectMother.commonQueryLanguageAkaZ3950());
    }
}
