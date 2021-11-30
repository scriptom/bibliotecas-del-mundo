package org.bibliotecasmundo.server.infrastructure.persistence.inmemory;

import com.google.common.collect.ImmutableList;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.domain.book.BookRepository;
import org.bibliotecasmundo.shared.domain.book.BookAuthor;
import org.bibliotecasmundo.shared.domain.book.BookTitle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private static final List<Book> books = ImmutableList.of(
            Book.create(BookTitle.create("El señor de los anillos"), BookAuthor.create("J.R.R. Tolkien")),
            Book.create(BookTitle.create("Harry Potter y la piedra filosofal"), BookAuthor.create("J.K. Rowling")),
            Book.create(BookTitle.create("El quijote"), BookAuthor.create("Cervantes")),
            Book.create(BookTitle.create("La divina comedia"), BookAuthor.create("Dante")),
            Book.create(BookTitle.create("El principito"), BookAuthor.create("Antoine de Saint-Exupéry")),
            Book.create(BookTitle.create("El hobbit"), BookAuthor.create("J.R.R. Tolkien"))
    );

    @Override
    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().getValue().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return books.stream().filter(book -> book.getTitle().getValue().equals(title)).findFirst();
    }
}
