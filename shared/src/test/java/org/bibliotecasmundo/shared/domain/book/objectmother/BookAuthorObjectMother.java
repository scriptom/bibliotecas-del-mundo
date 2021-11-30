package org.bibliotecasmundo.shared.domain.book.objectmother;

import org.bibliotecasmundo.shared.domain.book.BookAuthor;

public class BookAuthorObjectMother {
    public static BookAuthor jkRowling() {
        return BookAuthor.create("J.K. Rowling");
    }
}
