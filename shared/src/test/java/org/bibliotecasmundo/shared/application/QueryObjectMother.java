package org.bibliotecasmundo.shared.application;

import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.infrastructure.query.BookTitleQuery;

public class QueryObjectMother {
    public static Query harryPotterBookTitleQuery() {
        return new BookTitleQuery("Harry Potter", LibraryQueryLanguageObjectMother.spanishLibraryQueryLanguage());
    }
}
