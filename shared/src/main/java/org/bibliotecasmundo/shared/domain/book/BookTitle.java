package org.bibliotecasmundo.shared.domain.book;

import lombok.Value;
import org.bibliotecasmundo.shared.domain.ValueObject;

@Value(staticConstructor = "create")
public class BookTitle implements ValueObject<String> {
    String title;

    public static BookTitle inexistente() {
        return new BookTitle("");
    }

    @Override
    public String getValue() {
        return title;
    }
}
