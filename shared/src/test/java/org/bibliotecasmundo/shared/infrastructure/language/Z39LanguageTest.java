package org.bibliotecasmundo.shared.infrastructure.language;

import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.QueryLanguage;
import org.bibliotecasmundo.shared.infrastructure.query.QueryObjectMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Z39LanguageTest {

    @Test
    @DisplayName("Returns query \"as-is\" when translating from common language query string")
    void translateFromCommonLanguage() {
        QueryLanguage queryLanguage = Z39Language.getInstance();
        Query query = queryLanguage.translateFromCommonLanguage(Z39LanguageObjectMother.z39JKRowlingAuthorQueryString());
        Query expectedQuery = QueryObjectMother.commonLangJKRowlingAuthorQuery();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Returns query string \"as-is\" when translating to common language query object")
    void translateToCommonLanguage() {
        QueryLanguage queryLanguage = Z39Language.getInstance();
        Query query = QueryObjectMother.commonLangJKRowlingAuthorQuery();
        String translatedQuery = queryLanguage.translateToCommonLanguage(query);
        assertEquals(Z39LanguageObjectMother.z39JKRowlingAuthorQueryString(), translatedQuery);
    }
}
