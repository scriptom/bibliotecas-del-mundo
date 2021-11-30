package org.bibliotecasmundo.server.application;

import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.domain.book.Book;

import java.util.List;

public interface SearchBookUseCase {
    List<Book> search(Query query) throws InvalidSearchException;
}
