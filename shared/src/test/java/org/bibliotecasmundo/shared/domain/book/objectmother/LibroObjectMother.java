package org.bibliotecasmundo.shared.domain.book.objectmother;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bibliotecasmundo.shared.domain.book.Book;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LibroObjectMother {
    public static Book libroHarryPotter() {
        return Book.create(LibroTituloObjectMother.harryPotter(), BookAuthorObjectMother.jkRowling());
    }

    public static List<Book> librosPorJKRowling() {
        return Lists.newArrayList(libroHarryPotter(), Book.create(LibroTituloObjectMother.vacanteImprevista(), BookAuthorObjectMother.jkRowling()));
    }
}
