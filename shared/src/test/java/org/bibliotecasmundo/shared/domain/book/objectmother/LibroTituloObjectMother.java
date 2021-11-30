package org.bibliotecasmundo.shared.domain.book.objectmother;

import org.bibliotecasmundo.shared.domain.book.BookTitle;

public class LibroTituloObjectMother {
    public static BookTitle harryPotter() {
        return BookTitle.create("Harry Potter");
    }

    public static BookTitle vacanteImprevista() {
        return BookTitle.create("Vacante imprevista");
    }
}
