package org.bibliotecasmundo.shared.infrastructure.query;

import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.QueryParameter;
import org.bibliotecasmundo.shared.application.query.UntranslatableQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryFactoryTest {
    @Mock
    Language mockLanguage;

    @BeforeEach
    void prepareMock() {
        when(mockLanguage.getParameterName(QueryParameter.AUTHOR)).thenReturn("author");
        when(mockLanguage.getParameterName(QueryParameter.TITLE)).thenReturn("title");
    }

    @Test
    @DisplayName("Creates a query for author based on an author query string")
    void testParsesAuthorFromLanguage() {
        Query query = QueryFactory.parseFromLanguage(mockLanguage, "author J.K. Rowling");
        assertEquals("J.K. Rowling", query.getSearchTerm());
    }

    @Test
    @DisplayName("Creates a query for book title based on a title query string")
    void testParsesBookTitleFromLanguage() {
        Query query = QueryFactory.parseFromLanguage(mockLanguage, "title Harry Potter");
        assertEquals("Harry Potter", query.getSearchTerm());
    }

    @Test
    @DisplayName("Fails when the search parameter is not found within the language")
    void testFailsForUnknownLanguageOrToken() {
        assertThrows(UntranslatableQueryException.class, () -> QueryFactory.parseFromLanguage(mockLanguage, "Get Author J.K. Rowling"));
    }
}
