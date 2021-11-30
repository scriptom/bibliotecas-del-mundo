package org.bibliotecasmundo.shared.domain.book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findByAuthor(String author);
    Optional<Book> findByTitle(String title);
}
