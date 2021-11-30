package org.bibliotecasmundo.shared.domain.book;

import lombok.Value;
import org.bibliotecasmundo.shared.domain.ValueObject;

@Value(staticConstructor = "create")
public class BookAuthor implements ValueObject<String> {
    String author;

    public static BookAuthor inexistente() {
        return new BookAuthor("");
    }

    @Override
    public String getValue() {
        return author;
    }
}
