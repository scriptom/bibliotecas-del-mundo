package org.bibliotecasmundo.shared.application;

import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.application.query.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryLanguageTest {

    @Test
    @DisplayName("Can translate Z39 into own language")
    void translateFromCommonLanguage() {
        String z39Query = "Get Title Harry Potter";
        Language libraryLanguage = LibraryQueryLanguageObjectMother.spanishLibraryQueryLanguage();
        Query ownQuery = libraryLanguage.translateFromCommonLanguage(z39Query);
        String expected = QueryObjectMother.harryPotterBookTitleQuery().getQueryString();
        assertEquals(expected, ownQuery.getQueryString());
    }

    @Test
    @DisplayName("Can translate own language into Z39")
    void translateToCommonLanguage() {
        Query query = QueryObjectMother.harryPotterBookTitleQuery();
        Language libraryLanguage = LibraryQueryLanguageObjectMother.spanishLibraryQueryLanguage();
        String z39Query = libraryLanguage.translateToCommonLanguage(query);
        String expected = "Get Title Harry Potter";
        assertEquals(expected, z39Query);
    }
}
