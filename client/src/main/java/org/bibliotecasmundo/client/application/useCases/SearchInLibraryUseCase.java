package org.bibliotecasmundo.client.application.useCases;

import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.domain.book.Book;

import java.util.List;

public interface SearchInLibraryUseCase {
    List<Book> queryInLibrary(Query query, Library library);
}
