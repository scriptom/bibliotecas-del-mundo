package org.bibliotecasmundo.server.infrastructure.service;

import lombok.AllArgsConstructor;
import org.bibliotecasmundo.server.application.SearchBookUseCase;
import org.bibliotecasmundo.server.application.InvalidSearchException;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.domain.book.BookRepository;
import org.bibliotecasmundo.shared.infrastructure.query.AuthorQuery;
import org.bibliotecasmundo.shared.infrastructure.query.BookTitleQuery;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class LocalSearchBookSearchService implements SearchBookUseCase {
    private final BookRepository bookRepository;

    @Override
    public List<Book> search(Query query) {
        if (query instanceof BookTitleQuery) {
            return bookRepository.findByTitle(query.getSearchTerm()).map(Collections::singletonList).orElse(Collections.emptyList());
        }

        if (query instanceof AuthorQuery) {
            return bookRepository.findByAuthor(query.getSearchTerm());
        }

        throw new InvalidSearchException("Invalid query", query.getQueryString());
    }
}
