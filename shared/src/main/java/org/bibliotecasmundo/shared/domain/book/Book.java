package org.bibliotecasmundo.shared.domain.book;

import lombok.Data;

@Data(staticConstructor = "create")
public class Book {
    private final BookTitle title;
    private final BookAuthor author;

    private Book(BookTitle title, BookAuthor author) {
        this.title = title;
        this.author = author;
    }

    public static Book inexistente() {
        return new Book(BookTitle.inexistente(), BookAuthor.inexistente());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Book;
    }

}
