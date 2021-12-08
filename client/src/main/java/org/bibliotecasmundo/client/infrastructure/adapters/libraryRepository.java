package org.bibliotecasmundo.client.infrastructure.adapters;

import org.bibliotecasmundo.client.application.useCases.searchInLibrary;
import org.bibliotecasmundo.shared.application.query.LibraryQueryLanguage;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;

import java.util.Collections;

public class libraryRepository implements searchInLibrary{

    private LibraryQueryLanguage queryMaker;

    public libraryRepository(){
        queryMaker = new LibraryQueryLanguage("Biblioteca en Español", Collections.emptyMap());
    }

    @Override
    public String searchQuery(String query) {
        return null;
    }

}
