package org.bibliotecasmundo.client.infrastructure.adapters;

import org.bibliotecasmundo.client.application.useCases.searchInLibrary;
import org.bibliotecasmundo.shared.application.query.Query;

import java.util.Collections;

public class libraryRepository implements searchInLibrary{

    @Override
    public Query searchQuery(Query query) {
//        TODO: Funcion para hacer la busqueda y retornar el resultado en Z39
        return null;
    }

}
